package com.example.itemsorting.registry;

import com.example.itemsorting.ItemSortingMod;
import com.example.itemsorting.block.SorterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SORTER_BLOCK = new SorterBlock();

    public static void registerBlocks() {
        // 注册分类方块
        Registry.register(Registries.BLOCK, new Identifier(ItemSortingMod.MOD_ID, "sorter_block"), SORTER_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(ItemSortingMod.MOD_ID, "sorter_block"),
                new BlockItem(SORTER_BLOCK, new net.minecraft.item.Item.Settings()));

        // 将分类方块添加到物品组中
        ItemGroups.getGroup(ItemGroups.REDSTONE).getEntries().add(SORTER_BLOCK);
    }
}
