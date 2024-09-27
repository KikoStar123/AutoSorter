package com.example.itemsorting.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class SorterScreenHandler extends ScreenHandler {

    // 初始化 ScreenHandler
    public SorterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(null, syncId);
        // 您可以在此处添加物品槽逻辑
        // 示例：添加 9 个槽位
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
