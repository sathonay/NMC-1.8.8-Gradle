package com.nakory.modules.implementations.keystrokes;

import java.awt.Color;

import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class KeystrokesModule extends RenderableModule {

	

	private final KeystrokeKey[] keys = {
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindForward, 22, 0, 20, 20),
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindBack, 22, 22, 20, 20),
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindLeft, 0, 22, 20, 20),
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindRight, 44 , 22, 20, 20),
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindAttack, 0 , 44, 31, 20),
			new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindUseItem, 33 , 44, 31, 20)};
	
	@Override
	public int getHeight() {
		return 64;
	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	public void render(ScreenPosition position) {
		for (KeystrokeKey key : keys) key.render(position);
	}
	
	@Override
	public void renderDummy(ScreenPosition position) {
		for (KeystrokeKey key : keys) key.render(position);
	}
}