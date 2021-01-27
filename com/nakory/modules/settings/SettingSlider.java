package com.nakory.modules.settings;

import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider;

public class SettingSlider extends GuiSlider{

	public SettingSlider(GuiResponder guiResponder, int idIn, int x, int y, String name, float min, float max,
			float defaultValue, FormatHelper formatter) {
		super(guiResponder, idIn, x, y, name, min, max, defaultValue, formatter);
		// TODO Auto-generated constructor stub
	}

}
