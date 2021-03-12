package com.nakory.modules.implementations;

import com.nakory.hud.PropertyScreen;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FPSRModule extends RenderableModule {

	private String text = "0 fps";
	private FontRenderer fontRenderer;
	
	public FPSRModule() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		setBackgroundOffSet(-1, -1);
	}

	@Override
	public ScreenPosition getDefaultPosition() {
		return ScreenPosition.fromRelativePosition(0.50, 0);
	}

	@Override
	public void render(ScreenPosition position) {
		drawBackground(position.getAbsoluteX(), position.getAbsoluteY(), getWidth(), getHeight());
		Minecraft.getMinecraft().fontRendererObj.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), options.get("color").getColor().toRGBA());
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text = (Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? "0 fps" : Minecraft.getDebugFPS() + " fps"));
	}

	
	@Override
	public void renderDummy(ScreenPosition position) {
		render(position);
	}

	@Override
	public void save(ScreenPosition pos) {
		super.save(pos);
	}
}
