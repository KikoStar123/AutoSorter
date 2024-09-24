package com.example.itemsorting.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class TagEditorScreenHandler extends ScreenHandler {

    public TagEditorScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.TAG_EDITOR_SCREEN_HANDLER, syncId);
        // 初始化界面组件
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
