package com.nakory.modules.implementations.togglesprint;

import com.nakory.hud.IRenderer;
import com.nakory.hud.PropertyScreen;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ToggleSprintModule extends RenderableModule {

	private String text = "[Flying]";
	private FontRenderer fontRenderer;
	
	public boolean flyBoost = true;
	public float flyBoostFactor = 4;
	public int keyHoldTicks = 7;
	
	public ToggleSprintModule() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		setBackgroundOffSet(-1, -1);
	}
	
	@Override
	public ScreenPosition getDefaultPosition() {
		return ScreenPosition.fromRelativePosition(0.25, 0);
	}
	
	@Override
	public void render(ScreenPosition position) {
		drawBackground(position.getAbsoluteX(), position.getAbsoluteY(), getWidth(), getHeight());
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), options.get("color").getColor().toRGBA());
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		if (Minecraft.getMinecraft().currentScreen instanceof PropertyScreen) text = "[Flying]";
		else text = Minecraft.getMinecraft().thePlayer.movementInput.getDisplayText();
		if (text.equalsIgnoreCase("")) return -1;
		else return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		render(position);
	}
}