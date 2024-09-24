package com.example.itemsorting.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class TagItem extends Item {

    public TagItem() {
        super(new Settings().maxCount(1));
    }

    public static String getTag(ItemStack stack) {
        if (stack.getItem() instanceof TagItem) {
            NbtCompound nbt = stack.getNbt;
            if (nbt != null && nbt.contains("ItemTag")) {
                return nbt.getString("ItemTag");
            }
        }
        return "";
    }

}
