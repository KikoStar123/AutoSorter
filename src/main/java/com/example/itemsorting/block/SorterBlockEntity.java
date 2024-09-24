package com.example.itemsorting.block;

import com.example.itemsorting.item.TagItem;
import com.example.itemsorting.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类方块实体类，实现了分类方块的核心功能。
 * 它会在指定范围内扫描所有容器，根据物品的标签将其移动到对应的容器中。
 */
public class SorterBlockEntity extends BlockEntity {

    private static final int SCAN_INTERVAL = 100; // 每 5 秒执行一次扫描
    private static final int SCAN_RANGE = 10; // 扫描范围（可根据需要调整）

    public SorterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SORTER_BLOCK_ENTITY, pos, state);
    }

    /**
     * 方块实体的 tick 方法，由方块类的 getTicker 方法注册。
     * 这是一个静态方法，符合 BlockEntityTicker 接口的要求。
     * @param world 当前世界
     * @param pos 方块位置
     * @param state 方块状态
     * @param blockEntity 当前方块实体实例
     */
    public static void tick(World world, BlockPos pos, BlockState state, SorterBlockEntity blockEntity) {
        if (!world.isClient && world.getTime() % SCAN_INTERVAL == 0) {
            blockEntity.scanAndSort();
        }
    }

    /**
     * 扫描并分类物品的方法。
     */
    private void scanAndSort() {
        World world = this.getWorld();
        if (world == null) return;

        // 获取范围内的所有容器
        List<Inventory> inventories = getInventoriesInRange(world);

        // 遍历容器，分类物品
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.size(); slot++) {
                ItemStack stack = inventory.getStack(slot);
                if (!stack.isEmpty()) {
                    String itemTag = getItemTag(stack);
                    if (itemTag != null && !itemTag.isEmpty()) {
                        // 找到目标容器
                        Inventory targetInventory = findTargetInventory(itemTag, inventories, inventory);
                        if (targetInventory != null && targetInventory != inventory) {
                            // 尝试移动物品
                            if (moveItemStack(stack.copy(), targetInventory)) {
                                inventory.setStack(slot, ItemStack.EMPTY);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取指定范围内的所有容器。
     * @param world 当前世界
     * @return 包含所有容器的列表
     */
    private List<Inventory> getInventoriesInRange(World world) {
        List<Inventory> inventories = new ArrayList<>();
        BlockPos.streamOutwards(getPos(), SCAN_RANGE, SCAN_RANGE, SCAN_RANGE).forEach(pos -> {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof Inventory) {
                inventories.add((Inventory) be);
            }
        });
        return inventories;
    }

    /**
     * 从物品堆中获取自定义的物品标签。
     * @param stack 物品堆
     * @return 物品的标签，如果没有则返回空字符串
     */
    private String getItemTag(ItemStack stack) {
        NbtCompound nbt = stack.getTag();
        if (nbt != null && nbt.contains("ItemTag")) {
            return nbt.getString("ItemTag");
        }
        return "";
    }


    /**
     * 查找具有指定标签的目标容器。
     * @param tag 目标标签
     * @param inventories 容器列表
     * @param sourceInventory 源容器，避免自我循环
     * @return 目标容器，如果未找到则返回 null
     */
    private Inventory findTargetInventory(String tag, List<Inventory> inventories, Inventory sourceInventory) {
        for (Inventory inventory : inventories) {
            if (inventory == sourceInventory) continue;
            String inventoryTag = getInventoryTag(inventory);
            if (tag.equals(inventoryTag)) {
                return inventory;
            }
        }
        return null;
    }

    /**
     * 获取容器的标签，通过检查容器内是否有标签物品。
     * @param inventory 容器
     * @return 容器的标签，如果没有则返回空字符串
     */
    private String getInventoryTag(Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.getItem() instanceof TagItem) {
                return TagItem.getTag(stack);
            }
        }
        return "";
    }

    /**
     * 将物品堆移动到目标容器中。
     * @param stack 要移动的物品堆
     * @param targetInventory 目标容器
     * @return 如果成功移动，返回 true；否则返回 false
     */
    private boolean moveItemStack(ItemStack stack, Inventory targetInventory) {
        ItemStack remainingStack = stack.copy();
        for (int i = 0; i < targetInventory.size(); i++) {
            ItemStack targetStack = targetInventory.getStack(i);
            if (targetStack.isEmpty()) {
                targetInventory.setStack(i, remainingStack);
                return true;
            } else if (ItemStack.canCombine(remainingStack, targetStack)) {
                int transferAmount = Math.min(remainingStack.getCount(), targetStack.getMaxCount() - targetStack.getCount());
                if (transferAmount > 0) {
                    targetStack.increment(transferAmount);
                    remainingStack.decrement(transferAmount);
                    if (remainingStack.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        // 如果无法完全移动，返回是否有部分移动
        return remainingStack.getCount() != stack.getCount();
    }
}
