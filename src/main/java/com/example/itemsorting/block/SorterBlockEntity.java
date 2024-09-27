package com.example.itemsorting.block;

import com.example.itemsorting.registry.ModBlockEntities;
import com.example.itemsorting.util.ItemComparison; // 引用新的 ItemComparison 类
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class SorterBlockEntity extends BlockEntity {

    private static final int SCAN_INTERVAL = 100; // 每 5 秒执行一次扫描
    private static final int SCAN_RANGE = 10; // 扫描范围

    // 存储物品类型与其对应的容器位置
    private final Map<TagKey<Item>, BlockPos> categoryContainerMap = new HashMap<>();

    public SorterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SORTER_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SorterBlockEntity blockEntity) {
        if (!world.isClient && world.getTime() % SCAN_INTERVAL == 0) {
            blockEntity.scanAndSort();
        }
    }

    private void scanAndSort() {
        World world = this.getWorld();
        if (world == null) return;

        List<Inventory> inventories = getInventoriesInRange(world);

        // 自动为容器分配分类
        assignCategoryToContainers(inventories);

        // 遍历所有容器，分类物品
        for (Inventory inventory : inventories) {
            sortItemsInInventory(inventory, inventories);
        }
    }

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

    private void assignCategoryToContainers(List<Inventory> inventories) {
        for (Inventory inventory : inventories) {
            BlockPos containerPos = ((BlockEntity) inventory).getPos();

            // 跳过已经分配分类的容器
            if (categoryContainerMap.containsValue(containerPos)) continue;

            // 获取容器内的第一个非空物品
            ItemStack firstItem = getFirstNonEmptyItem(inventory);
            if (!firstItem.isEmpty()) {
                TagKey<Item> categoryTag = determineTagCategory(firstItem);

                // 如果该类型的物品还没有对应的容器，则将当前容器分配给它
                if (!categoryContainerMap.containsKey(categoryTag)) {
                    categoryContainerMap.put(categoryTag, containerPos);
                }
            }
        }
    }

    private ItemStack getFirstNonEmptyItem(Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    // 使用标签来判断物品分类
    private TagKey<Item> determineTagCategory(ItemStack stack) {
        // 检查物品是否属于石头类标签
        if (ItemComparison.isStoneItem(stack)) {
            return ItemComparison.STONE_TAG;
        }

        // 如果不是石头类物品，可以返回其他分类标签或 null
        return null;
    }

    private void sortItemsInInventory(Inventory inventory, List<Inventory> inventories) {
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getStack(slot);
            if (!stack.isEmpty()) {
                TagKey<Item> itemCategoryTag = determineTagCategory(stack);
                Inventory targetInventory = findTargetInventory(itemCategoryTag, inventories, inventory);
                if (targetInventory != null && targetInventory != inventory) {
                    if (moveItemStack(stack.copy(), targetInventory)) {
                        inventory.setStack(slot, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    private Inventory findTargetInventory(TagKey<Item> categoryTag, List<Inventory> inventories, Inventory sourceInventory) {
        BlockPos targetPos = categoryContainerMap.get(categoryTag);

        // 如果没有现有的容器，尝试寻找一个空容器并分配给该物品类型
        if (targetPos == null) {
            for (Inventory inventory : inventories) {
                BlockPos containerPos = ((BlockEntity) inventory).getPos();

                // 如果容器未被分配给任何分类，分配给当前分类
                if (!categoryContainerMap.containsValue(containerPos)) {
                    categoryContainerMap.put(categoryTag, containerPos);
                    return inventory;
                }
            }
            return null; // 没有空容器可用
        }

        // 找到已分配的容器
        for (Inventory inventory : inventories) {
            BlockPos containerPos = ((BlockEntity) inventory).getPos();
            if (containerPos.equals(targetPos) && inventory != sourceInventory) {
                return inventory;
            }
        }
        return null;
    }

    // 检查两个 ItemStack 是否可以合并
    private boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsEqual(stack1, stack2);
    }

    private boolean moveItemStack(ItemStack stack, Inventory targetInventory) {
        ItemStack remainingStack = stack.copy();
        for (int i = 0; i < targetInventory.size(); i++) {
            ItemStack targetStack = targetInventory.getStack(i);
            if (targetStack.isEmpty()) {
                targetInventory.setStack(i, remainingStack);
                return true;
            } else if (canCombine(remainingStack, targetStack)) {
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
        return remainingStack.getCount() != stack.getCount();
    }
}
