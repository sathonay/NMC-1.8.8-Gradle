package com.nakory.modules.implementations.keystrokes;

import java.util.Map;

import com.nakory.gui.button.ColorChooser;
import com.nakory.gui.button.CustomizableColor;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;

public class KeystrokesModule extends RenderableModule {

	

	private final KeystrokeKey[] keys = {
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindForward, 22, 0, 20, 20),
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindBack, 22, 22, 20, 20),
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindLeft, 0, 22, 20, 20),
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindAttack, 0 , 44, 31, 20),
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindRight, 44 , 22, 20, 20),
			new KeystrokeKey(this, Minecraft.getMinecraft().gameSettings.keyBindUseItem, 33 , 44, 31, 20)};

	@Override
	public Map<String, ColorChooser> getDefaultOptions() {
		Map<String, ColorChooser> options = super.getDefaultOptions();
		options.get("color").displayString = "Text Color Up";
		ColorChooser background = options.remove("background");
		background.displayString = "Background Up";
		options.put("color_down", new ColorChooser("Text Color Down", new CustomizableColor(0, 0, 0, 255)));
		options.put("background", background);
		options.put("background_down", new ColorChooser("Background Down", new CustomizableColor(255, 255, 255, 102)));
		return options;
	}

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
