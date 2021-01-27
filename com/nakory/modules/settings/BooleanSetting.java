package com.nakory.modules.settings;

import net.minecraft.util.EnumChatFormatting;

public class BooleanSetting extends Setting<Boolean>{

	public BooleanSetting(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDisplay() {
		this.displayString = displayBase + (getValue() ? EnumChatFormatting.GREEN + "Enable" : EnumChatFormatting.RED + "Disable");
	}
}
