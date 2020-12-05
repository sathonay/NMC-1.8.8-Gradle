package com.nakory.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiClientOptionsChat extends GuiClientOptions {

	@Override
	public void initGui() {
		addButton(new GuiButton(0, 0, 0, "Render background: " + (settings.renderChatBackground ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.renderChatBackground = !settings.renderChatBackground;
			button.displayString = "Render background: " + (settings.renderChatBackground ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
		});
		
		addButton(new GuiButton(1, 0, 20, "Render background on toggle: " + (settings.renderChatBackgroundToggle ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.renderChatBackgroundToggle = !settings.renderChatBackgroundToggle;
			button.displayString = "Render background on toggle: " + (settings.renderChatBackgroundToggle ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
			settings.save();
		});
		
		addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptions());
		});
	}
}
