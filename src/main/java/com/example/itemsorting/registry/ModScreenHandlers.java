package com.example.itemsorting.registry;

import com.example.itemsorting.ItemSortingMod;
import com.example.itemsorting.screen.TagEditorScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static ScreenHandlerType<TagEditorScreenHandler> TAG_EDITOR_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        TAG_EDITOR_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(ItemSortingMod.MOD_ID, "tag_editor_screen_handler"),
                new ScreenHandlerType<>(TagEditorScreenHandler::new)
        );
    }
}
