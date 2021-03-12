package com.nakory.gui;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import com.nakory.NakoryClient;

import com.nakory.gui.button.CustomizableColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiClientOptions extends GuiScreen {
	
	protected NakorySettings settings = NakoryClient.getInstance().getSettings();
	private final Map<GuiButton, Consumer<GuiButton>> actions = new HashMap<>();

	private static CustomizableColor color = new CustomizableColor();
	
	@Override
	public void initGui() {
		addButton(new GuiButton(0, 0, 0, "Scoreboard Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsScoreboard());
		});

		addButton(new GuiButton(0, 0, 20, "Chat Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsChat());
		});
		
		addButton(new GuiButton(0, 0, 40, "Toggle Sneak & Sprint Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsToggleSneak());
		});

		addButton(new GuiButton(0, 0, 40, "Menu Settings"), (button) -> {
			this.mc.displayGuiScreen(new GuiClientOptionsMenu());
		});
		
		addButton(new GuiButton(0, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])), (button) -> {
			this.mc.displayGuiScreen(new GuiIngameMenu());
		});

		/*ColorChooser colorChooser = new ColorChooser("Color", color);
		addButton(colorChooser, button -> {
			if (button instanceof ColorChooser) ((ColorChooser) button).openColorChooser();
		});*/

		int height = 0;
		for (int i = buttonList.size() - 1; i >= 0; i--) {
			GuiButton button = buttonList.get(i);
			button.xPosition = (this.width - button.getButtonWidth()) / 2;
			button.yPosition = this.height / 2 - height - button.getButtonHeight();
			height += button.getButtonHeight() + 4;
		}
	}
	
	protected void addButton(GuiButton button, Consumer<GuiButton> action) {
		this.buttonList.add(button);
		this.actions.put(button, action);
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		Consumer<GuiButton> action = actions.get(button);
		if (action != null) action.accept(button);
	}
}
