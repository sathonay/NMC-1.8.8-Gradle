package com.nakory.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiClientOptionsToggleSneak extends GuiClientOptions {
	
	@Override
	public void initGui() {
		
		addButton(new GuiButton(0, 0, 0, "Toggle Sneak: " + (settings.toggleSneak ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.toggleSneak = !settings.toggleSneak;
			button.displayString = "Toggle Sneak: " + (settings.toggleSneak ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
			settings.save();
		});
		
		addButton(new GuiButton(1, 0, 20, "Toggle Sprint: " + (settings.toggleSprint ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.toggleSprint = !settings.toggleSprint;
			button.displayString = "Toggle Sprint: " + (settings.toggleSprint ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
			settings.save();
		});
		
		addButton(new GuiButton(2, 0, 40, "Flying Boost: " + (settings.toggleFlyingBoost ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.toggleFlyingBoost = !settings.toggleFlyingBoost;
			button.displayString = "Flying Boost: " + (settings.toggleFlyingBoost ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
			settings.save();
		});
		
		addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptions());
		});
		int height = 0;
		for (GuiButton button : buttonList) {
			button.xPosition = (width - button.getButtonWidth()) / 2;
			button.yPosition = (this.height - button.getButtonHeight()) / 3 + height;
			height += button.getButtonHeight() + 4;
		}
	}
}
