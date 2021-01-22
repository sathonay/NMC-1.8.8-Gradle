package com.nakory.modules.implementations;

import java.util.ArrayList;
import java.util.List;

import com.nakory.hud.PropertyScreen;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class NearPlayersModule extends RenderableModule {

	private List<String> lines;
	private int width, height;
	
	@Override
	public int getHeight() {
		return lines.size() * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		List<String> lines = new ArrayList<>();
		lines.add("Near Player(s)");
		
		int width = 0;
		
		if (Minecraft.getMinecraft().currentScreen instanceof PropertyScreen) {
			lines.add(" - Name (x, y, z)");
		} else {
			for (Entity entity : Minecraft.getMinecraft().thePlayer.worldObj.loadedEntityList) {
				if (entity instanceof EntityPlayer) {
					BlockPos pos = entity.getPosition();
					lines.add(" - " + entity.getName() + " (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
				}
			}
		}
		
		for (String line : lines) {
			int lineWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(line);
			if (lineWidth > width) width = lineWidth;
		}
		
		this.lines = lines;
		return width;
	}

	@Override
	public void render(ScreenPosition position) {
		int index = 0;
		for (String line : lines) {
			Minecraft.getMinecraft().fontRendererObj.drawString(line, position.getAbsoluteX(), position.getAbsoluteY() + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * index++), 0xFFFFFF);
		}
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		int index = 0;
		for (String line : lines) {
			Minecraft.getMinecraft().fontRendererObj.drawString(line, position.getAbsoluteX(), position.getAbsoluteY() + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * index++), 0xFFFFFF);
		}
	}
}
