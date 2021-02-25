package com.nakory.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.nakory.NakoryClient;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import optifine.Lang;

public class GuiClientOptions extends GuiScreen {
	
	protected NakorySettings settings = NakoryClient.getInstance().getSettings();
	
	private final Map<Integer, Consumer<GuiButton>> actions = new HashMap<>();
	
	@Override
	public void initGui() {
		addButton(new GuiButton(0, 0, 0, "Scoreboard Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsScoreboard());
		});

		addButton(new GuiButton(1, 0, 20, "Chat Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsChat());
		});
		
		addButton(new GuiButton(2, 0, 40, "Toggle Sneak & Sprint Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsToggleSneak());
		});
		
		addButton(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiIngameMenu());
		});
		
		
		int height = 0;
		for (GuiButton button : buttonList) {
			button.xPosition = (width - button.getButtonWidth()) / 2;
			button.yPosition = (this.height - button.getButtonHeight()) / 3 + height;
			height += button.getButtonHeight() + 4;
		}
	}
	
	protected void addButton(GuiButton button, Consumer<GuiButton> action) {
		this.buttonList.add(button);
		this.actions.put(button.id, action);
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		Consumer<GuiButton> action = actions.get(button.id);
		if (action != null) action.accept(button);
	}
}
