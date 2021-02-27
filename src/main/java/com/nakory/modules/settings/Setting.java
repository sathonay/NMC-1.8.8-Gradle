package com.nakory.modules.settings;

import net.minecraft.client.gui.GuiButton;

public class Setting<V> extends GuiButton {

	protected String displayBase;
	
	public Setting(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.displayBase = buttonText;
		updateDisplay();
		// TODO Auto-generated constructor stub
	}

	private V value;
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public void updateDisplay() {}
	
	public V getValue() {
		return value;
	}
}
