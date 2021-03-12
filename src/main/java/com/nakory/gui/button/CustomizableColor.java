package com.nakory.gui.button;

import java.awt.*;
import java.util.Objects;

public class CustomizableColor {

    private int red, green, blue, alpha;



    public CustomizableColor(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public CustomizableColor(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public CustomizableColor() {
        this(255, 255, 255, 255);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public Color toColor() {
        return new Color(red, green, blue, alpha);
    }

    public int toRGBA() {
        return toColor().getRGB();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomizableColor that = (CustomizableColor) o;
        return red == that.red &&
                green == that.green &&
                blue == that.blue &&
                alpha == that.alpha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, alpha);
    }

    @Override
    public String toString() {
        return "CustomizableColor{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }
}
