package com.nakory.modules.implementations;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CPSModule  extends RenderableModule {

	private String text = "10 cps";
	private FontRenderer fontRenderer;
	
	public CPSModule() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}
	
	@Override
	public ScreenPosition getDefaultPosition() {
		// TODO Auto-generated method stub
		return ScreenPosition.fromRelativePosition(0.65, 0);
	}
	
	private boolean wasPressed;
	private long lastPressed;

	@Override
	public void render(ScreenPosition position) {
		
		final boolean pressed = Mouse.isButtonDown(0);
		
		if (pressed != this.wasPressed) {
			this.wasPressed = pressed;
			this.lastPressed = System.currentTimeMillis();
			if (pressed) this.clicks.add(this.lastPressed);
		}
		
		Minecraft.getMinecraft().fontRendererObj.drawString(getCPS() + " cps", position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
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
	
	private final List<Long> clicks = new ArrayList<>();
	
	private int getCPS() {
		final long time = System.currentTimeMillis();
		this.clicks.removeIf(aLong -> aLong + 1000 < time);
		return this.clicks.size();
	}
}
