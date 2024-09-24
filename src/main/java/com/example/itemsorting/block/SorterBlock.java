package com.example.itemsorting.block;

import com.mojang.serialization.Codec;
import com.example.itemsorting.registry.ModBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SorterBlock extends BlockWithEntity {

    public SorterBlock() {
        super(FabricBlockSettings.create()
                .strength(4.0f)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SorterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <E extends BlockEntity> BlockEntityTicker<E> getTicker(World world, BlockState state, BlockEntityType<E> type) {
        return world.isClient ? null : createTickerHelper(type, ModBlockEntities.SORTER_BLOCK_ENTITY, SorterBlockEntity::tick);
    }

    @Nullable
    protected static <E extends BlockEntity> BlockEntityTicker<E> createTickerHelper(BlockEntityType<E> givenType, BlockEntityType<? extends BlockEntity> expectedType, BlockEntityTicker<? super BlockEntity> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<E>) ticker : null;
    }

    // 修改方法名为 getCodec()，以匹配 BlockWithEntity 中的抽象方法
    @Override
    public Codec<? extends Block> getCodec() {
        return Codec.unit(this);
    }
}
