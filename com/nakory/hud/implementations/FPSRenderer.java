package com.nakory.hud.implementations;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FPSRenderer implements IRenderer{

	private String text = "1000 fps";
	private FontRenderer fontRenderer;
	
	public FPSRenderer() {
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
		Minecraft.getMinecraft().fontRendererObj.drawString(Minecraft.getDebugFPS() + " fps", position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
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
	
	@Override
	public boolean isEnabled() {
		return enable;
	}
}