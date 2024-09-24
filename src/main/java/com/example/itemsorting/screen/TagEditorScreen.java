package com.example.itemsorting.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class TagEditorScreen extends HandledScreen<TagEditorScreenHandler> {

    private TextFieldWidget tagField;
    private ButtonWidget saveButton;

    public TagEditorScreen(TagEditorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        tagField = new TextFieldWidget(textRenderer, x + 10, y + 10, 150, 20, Text.literal("标签"));
        addDrawableChild(tagField);

        saveButton = new ButtonWidget(x + 10, y + 40, 80, 20, Text.literal("保存"), button -> {
            // 保存标签到手持物品
            String tag = tagField.getText();
            if (!tag.isEmpty()) {
                saveItemTag(tag);
            }
        });
        addDrawableChild(saveButton);
    }

    private void saveItemTag(String tag) {
        if (client != null && client.player != null) {
            ItemStack heldItem = client.player.getMainHandStack();
            if (!heldItem.isEmpty()) {
                NbtCompound nbt = heldItem.getOrCreateNbt();
                nbt.putString("ItemTag", tag);
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        tagField.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, textRenderer, title, width / 2, 20, 0xFFFFFF);
    }
}
