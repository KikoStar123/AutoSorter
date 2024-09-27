package com.example.itemsorting.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ItemComparison {

    // 定义石头类标签
    public static final TagKey<Item> STONE_TAG = TagKey.of(Registries.ITEM.getKey(), new Identifier("autosorter", "stones"));

    // 检查 ItemStack 是否属于石头类
    public static boolean isStoneItem(ItemStack stack) {
        return stack.isIn(STONE_TAG);
    }
}
