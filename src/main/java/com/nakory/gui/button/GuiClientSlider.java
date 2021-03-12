package com.nakory.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class GuiClientSlider extends GuiButton {
    private float sliderValue;
    public boolean dragging;

    private String text;
    private final float min;
    private final float max;

    public GuiClientSlider(String text, int id, int x, int y)
    {
        this(text, id, x, y, 0.0F, 1.0F, 0);
    }

    public GuiClientSlider(String text, int id, int x, int y, float min, float max, float value)
    {
        super(id, x, y, 150, 20, "");
        this.text = text;
        this.sliderValue = value / max;
        this.min = min;
        this.max = max;
        this.displayString = text + toInt();
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
                this.displayString = text + toInt();
            }

            /*mc.getTextureManager().bindTexture(buttonTextures);*/
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 6)), this.yPosition, this.xPosition + (int)(this.sliderValue * (float)(this.width - 6)) + 6, this.yPosition + 20, new Color(189, 195, 199).getRGB());
            /*this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);*/
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (super.mousePressed(mc, mouseX, mouseY))
        {
            this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
            this.displayString = text + toInt();
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public int toInt() {
        return (int) MathHelper.clamp_float(max *  this.sliderValue, min, max);
    }

    public void intToSliderValue(int value) {
        if (value == 0 ) sliderValue = 0;
        else sliderValue = MathHelper.clamp_float(value, min, max) / max;
        displayString = text + toInt();
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
        this.dragging = false;
    }
}
