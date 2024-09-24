package com.example.itemsorting;

import net.fabricmc.api.ModInitializer;

public class ItemSortingMod implements ModInitializer {

    public static final String MOD_ID = "itemsorting";

    @Override
    public void onInitialize() {
        // 注册方块、物品、事件等
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModScreenHandlers.registerScreenHandlers();
    }
}
