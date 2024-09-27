package com.example.itemsorting.block;

import com.example.itemsorting.registry.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SorterBlock extends BlockWithEntity {

    // 构造函数，接受 FabricBlockSettings 作为参数
    public SorterBlock(FabricBlockSettings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SorterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <E extends BlockEntity> BlockEntityTicker<E> getTicker(World world, BlockState state, BlockEntityType<E> type) {
        return type == ModBlockEntities.SORTER_BLOCK_ENTITY ? (BlockEntityTicker<E>) (BlockEntityTicker<SorterBlockEntity>) SorterBlockEntity::tick : null;
    }

    @Override
    public MapCodec<? extends BlockWithEntity> getCodec() {
        // 正确实现 getCodec() 方法
        return null; // 如果您的方块不需要数据驱动，请返回 null
    }


}
