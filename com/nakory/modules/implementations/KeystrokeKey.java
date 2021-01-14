package com.nakory.modules.implementations;

import java.awt.Color;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

public class KeystrokeKey {

	private final KeyBinding key;
	private int lastKeyCode = 0;
	private String keyName = "None";

	private final int xOffSet;
	private final int yOffSet;
	private final int width;
	private final int height;
	
	public KeystrokeKey(KeyBinding key, int xOffSet, int yOffSet, int width, int height) {
		this.key = key;
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
		this.width = width;
		this.height = height;
	} 
	
	public String getKeyName() {
		
		int keyCode = key.getKeyCode();
		if (keyCode != lastKeyCode) {
			lastKeyCode = keyCode;
			if (keyCode < 0) {
				if (keyCode == -100) keyName = "LMB";
				else if (keyCode == -99) keyName = "RMB";
				else keyName = "MB";
			} else {
				keyName = Keyboard.getKeyName(keyCode);
			}
		}
		
		return keyName;
	}
	
	public boolean isDown() {
		return key.isKeyDown();
	}
	
	public void render(ScreenPosition position) {
		GuiScreen.drawRect(position.getAbsoluteX() + xOffSet, position.getAbsoluteY() + yOffSet, position.getAbsoluteX() + xOffSet + width, position.getAbsoluteY() + yOffSet + height, isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 102).getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawString(
				getKeyName(), position.getAbsoluteX() + xOffSet + (width / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(getKeyName()) / 2),
				position.getAbsoluteY() + yOffSet + (height / 2) - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2),
				isDown()  ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
	}
}
