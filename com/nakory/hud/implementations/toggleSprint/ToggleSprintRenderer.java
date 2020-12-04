package com.nakory.hud.implementations.toggleSprint;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ToggleSprintRenderer implements IRenderer{

	private String text = "[Sprinting (Key Toggled))]";
	private FontRenderer fontRenderer;
	
	public boolean flyBoost = true;
	public float flyBoostFactor = 4;
	public int keyHoldTicks = 7;
	
	public ToggleSprintRenderer() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}
	
	ScreenPosition pos;
	
	@Override
	public void save(ScreenPosition position) {
		pos = position;
	}

	@Override
	public ScreenPosition load() {
		return pos;
	}

	@Override
	public void render(ScreenPosition position) {
		Minecraft.getMinecraft().fontRendererObj.drawString(Minecraft.getMinecraft().thePlayer.movementInput.getDisplayText(), position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
	}
	
	
	private boolean enable = true;
	
	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isModEnabled() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return enable;
	}
}