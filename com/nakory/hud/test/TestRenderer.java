package com.nakory.hud.test;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class TestRenderer implements IRenderer{

	private String text = "HELLO WORLD";
	private FontRenderer fontRenderer;
	
	public TestRenderer() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}
	
	@Override
	public void save(ScreenPosition position) {
		TestConfig.X_1 = position.getRelativeX();
		TestConfig.Y_1 = position.getRelativeY();
	}

	@Override
	public ScreenPosition load() {
		return ScreenPosition.fromRelativePosition(TestConfig.X_1, TestConfig.Y_1);
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
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth("HELLO WORLD");
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
	}

}
