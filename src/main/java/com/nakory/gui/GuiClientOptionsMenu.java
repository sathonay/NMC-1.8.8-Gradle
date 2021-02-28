package com.nakory.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiClientOptionsMenu extends GuiClientOptions {

    @Override
    public void initGui() {
        addButton(new GuiButton(0, 0, 0, "Render menu background: " + (settings.renderMenuBackground ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
            settings.renderMenuBackground = !settings.renderMenuBackground;
            button.displayString = "Render menu background: " + (settings.renderMenuBackground ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
        });

        addButton(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
            this.mc.displayGuiScreen(new GuiClientOptions());
        });
        int height = 0;
        for (int i = buttonList.size() - 1; i >= 0; i--) {
            GuiButton button = buttonList.get(i);
            button.xPosition = (this.width - button.getButtonWidth()) / 2;
            button.yPosition = this.height / 2 - height - button.getButtonHeight();
            height += button.getButtonHeight() + 4;
        }
    }
}