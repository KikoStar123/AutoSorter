package com.example.itemsorting.registry;

import com.example.itemsorting.block.SorterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

public class ModBlocks {
    // 初始化自定义方块
    public static final Block SORTER_BLOCK = new SorterBlock(FabricBlockSettings.create().strength(4.0f).requiresTool());

    public static void registerBlocks() {
        // 注册方块到 Registries.BLOCK
        Registry.register(Registries.BLOCK, new Identifier("autosorter", "sorter_block"), SORTER_BLOCK);
    }
}
