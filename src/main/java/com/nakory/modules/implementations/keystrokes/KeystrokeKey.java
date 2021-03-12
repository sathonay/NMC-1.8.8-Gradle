package com.nakory.modules.implementations.keystrokes;

import java.awt.Color;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;

public class KeystrokeKey {

	private KeystrokesModule module;

	private final KeyBinding key;
	private int lastKeyCode = 0;
	private String keyName = "None";

	private final int xOffSet;
	private final int yOffSet;
	private final int width;
	private final int height;
	
	public KeystrokeKey(KeystrokesModule module, KeyBinding key, int xOffSet, int yOffSet, int width, int height) {
		this.module = module;
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
			} else if (keyCode == 0) keyName = "None";
			else keyName = Keyboard.getKeyName(keyCode);
		}
		
		return keyName;
	}
	
	public boolean isDown() {
		return key.isKeyDown();
	}
	
	public void render(ScreenPosition position) {
        Gui.drawRect(
        		position.getAbsoluteX() + this.xOffSet,
        		position.getAbsoluteY() + this.yOffSet,
        		position.getAbsoluteX() + this.xOffSet + this.width,
        		position.getAbsoluteY() + this.yOffSet + this.height,
        		this.isDown() ? module.options.get("background_down").getColor().toRGBA() : module.options.get("background").getColor().toRGBA());
        Minecraft.getMinecraft().fontRendererObj.drawString(
        		this.getKeyName(),
        		position.getAbsoluteX() + (float)this.xOffSet + (this.width / 2) - ((Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getKeyName()) - 2) / 2),
        		position.getAbsoluteY() + (float)this.yOffSet + (this.height / 2) - ((Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 3) / 2),
        		this.isDown() ? module.options.get("color_down").getColor().toRGBA() : module.options.get("color").getColor().toRGBA() , false);
	}
}
