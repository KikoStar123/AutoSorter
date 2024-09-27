package com.example.itemsorting.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModItems {
    // 初始化方块物品
    public static final Item SORTER_BLOCK_ITEM = new BlockItem(ModBlocks.SORTER_BLOCK, new Item.Settings());

    public static void registerItems() {
        // 注册方块物品到 Registries.ITEM
        Registry.register(Registries.ITEM, new Identifier("autosorter", "sorter_block"), SORTER_BLOCK_ITEM);
    }
}
