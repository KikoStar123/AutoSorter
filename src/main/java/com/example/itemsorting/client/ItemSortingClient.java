package com.example.itemsorting.client;

import com.example.itemsorting.registry.ModScreenHandlers;
import com.example.itemsorting.screen.TagEditorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ItemSortingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.TAG_EDITOR_SCREEN_HANDLER, TagEditorScreen::new);
    }
}
