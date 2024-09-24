package com.example.itemsorting.registry;

import com.example.itemsorting.ItemSortingMod;
import com.example.itemsorting.block.SorterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static BlockEntityType<SorterBlockEntity> SORTER_BLOCK_ENTITY;

    public static void registerBlockEntities() {
        SORTER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ItemSortingMod.MOD_ID, "sorter_block_entity"),
                FabricBlockEntityTypeBuilder.create(SorterBlockEntity::new, ModBlocks.SORTER_BLOCK).build(null)
        );
    }
}
