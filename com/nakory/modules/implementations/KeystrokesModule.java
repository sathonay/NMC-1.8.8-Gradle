package com.nakory.modules.implementations;

import java.awt.Color;

import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;

public class KeystrokesModule extends RenderableModule {

	
	/*private KeystrokeKey forward = new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindForward, 22, 0);
	private KeystrokeKey back = new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindBack, 22, 22);
	private KeystrokeKey left = new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindLeft, 0, 22);
	private KeystrokeKey right = new KeystrokeKey(Minecraft.getMinecraft().gameSettings.keyBindRight, 44 , 22);*/
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
