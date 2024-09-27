package com.example.itemsorting;

import com.example.itemsorting.registry.ModBlocks;
import com.example.itemsorting.registry.ModBlockEntities;
import com.example.itemsorting.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class ItemSortingMod implements ModInitializer {

    @Override
    public void onInitialize() {
        // 注册方块、方块实体、以及物品
        ModBlocks.registerBlocks();
        ModBlockEntities.registerBlockEntities();
        ModItems.registerItems();
    }
}
