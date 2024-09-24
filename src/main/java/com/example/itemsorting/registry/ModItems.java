package com.example.itemsorting.registry;

import com.example.itemsorting.ItemSortingMod;
import com.example.itemsorting.item.TagEditorItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TAG_EDITOR = new TagEditorItem();

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(ItemSortingMod.MOD_ID, "tag_editor"), TAG_EDITOR);

        // 将标签编辑器添加到物品组中
        ItemGroups.getGroup(ItemGroups.TOOLS).getEntries().add(TAG_EDITOR);
    }
}
