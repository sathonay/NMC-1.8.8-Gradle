package com.nakory.gui;


import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiClientButton extends GuiButton{

	public GuiClientButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
		// TODO Auto-generated constructor stub
	}

	private int animation = 0;
	private int color;
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	        this.mouseDragged(mc, mouseX, mouseY);
	        if (this.hovered) {
	        	if (animation < 4) {
	        		animation++;
	        	}
	        	this.color = new Color(255, 255, 255).getRGB();
	        } else {
	        	if (animation > 0) {
	        		animation--;
	        	}
	        	this.color = new Color(50, 50, 50).getRGB();
	        }
	        this.drawRect(this.xPosition + animation, this.yPosition, this.xPosition + this.width - animation, this.yPosition + this.height, this.color);
	        this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + (this.width / 2), this.yPosition + (this.height - 8) / 2, new Color(255, 0, 0).getRGB());
		}
	}
}
