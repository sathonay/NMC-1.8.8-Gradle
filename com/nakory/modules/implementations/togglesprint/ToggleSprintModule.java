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
	}
	
	@Override
	public ScreenPosition getDefaultPosition() {
		return ScreenPosition.fromRelativePosition(0.25, 0);
	}
	
	@Override
	public void render(ScreenPosition position) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? text = "[Flying]" : (text = Minecraft.getMinecraft().thePlayer.movementInput.getDisplayText()));
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
	}
}