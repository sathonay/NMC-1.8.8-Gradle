package com.nakory.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiClientOptionsToggleSneak extends GuiClientOptions {
	
	@Override
	public void initGui() {
		
		addButton(new GuiButton(0, 0, 0, "Toggle Sneak: " + (toggleSneak ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			toggleSneak = !toggleSneak;
			button.displayString = "Toggle Sneak: " + (toggleSneak ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
		});
		
		addButton(new GuiButton(1, 0, 20, "Toggle Sprint: " + (toggleSprint ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			toggleSprint = !toggleSprint;
			button.displayString = "Toggle Sprint: " + (toggleSprint ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
		});
		
		addButton(new GuiButton(2, 0, 40, "Flying Boost: " + (toggleFlyingBoost ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			toggleFlyingBoost = !toggleFlyingBoost;
			button.displayString = "Flying Boost: " + (toggleFlyingBoost ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
		});
		
		addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptions());
		});
	}
}
