package com.nakory.gui;

import java.util.function.Consumer;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiClientOptionsScoreboard extends GuiClientOptions {
	
	@Override
	public void initGui() {
		addButton(new GuiButton(0, 0, 0, "Line Numbers: " + (settings.showRedNumberInScoreboard ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable")), (button) -> {
			settings.showRedNumberInScoreboard = !settings.showRedNumberInScoreboard;
			button.displayString = "Line Numbers: " + (settings.showRedNumberInScoreboard ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
			settings.save();
		});
		
		addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptions());
		});
	}
}
