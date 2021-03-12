package com.nakory.gui.button;

import com.nakory.NakoryClient;
import com.nakory.modules.RenderableModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

import java.awt.*;
import java.io.IOException;

public class ColorChooser extends GuiButton {
    private final CustomizableColor color;

    public ColorChooser(String text, CustomizableColor color) {
        super(0, 0, 0, text);
        System.out.println(color);
        this.color = color;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (isMouseOver()) openColorChooser();
        return super.mousePressed(mc, mouseX, mouseY);
    }

    public void openColorChooser() {
        Minecraft.getMinecraft().displayGuiScreen(new ColorGUI());
    }

    private class ColorGUI extends GuiScreen {

        private GuiClientSlider red, green, blue, alpha;
        private GuiTextField textField = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, (width - 75) / 2, height / 2 + 20, 150, 20);

        private GuiScreen previousScreen;

        public ColorGUI() {
            previousScreen = Minecraft.getMinecraft().currentScreen;
        }

        @Override
        public void initGui() {
            super.initGui();
            red = new GuiClientSlider("Red: ", 0, 60, 0, 0, 255, color.getRed());
            green = new GuiClientSlider("Green: ",0, 60, 20, 0, 255, color.getGreen());
            blue = new GuiClientSlider("Blue: ",0, 60, 40, 0, 255, color.getBlue());
            alpha = new GuiClientSlider("Alpha: ",0, 60, 60, 0, 255, color.getAlpha());
            buttonList.add(red);
            buttonList.add(green);
            buttonList.add(blue);
            buttonList.add(alpha);
            buttonList.add(new GuiButton(1, 0, 0, 150, 20, "Done"));
            textField.setMaxStringLength(9);
            System.out.println(color);
            int height = 0;
            for (int i = buttonList.size() - 1; i >= 0; i--) {
                GuiButton button = buttonList.get(i);
                button.xPosition = (this.width - button.getButtonWidth()) / 2;
                button.yPosition = this.height / 2 - height - button.getButtonHeight();
                height += button.getButtonHeight() + 4;
            }
            updateColor();
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawScreen(mouseX, mouseY, partialTicks);
            textField.xPosition = (width - 150) / 2;
            textField.yPosition = height / 2 + 20;
            int buttonWidest = 0;
            for (GuiButton button : this.buttonList) if (button.getButtonWidth() > buttonWidest) buttonWidest = button.getButtonWidth();
            drawRect((width + buttonWidest) / 2 + 80 , height / 2 - 80, (width + buttonWidest) / 2 + 160, height / 2, color.toRGBA());
            textField.drawTextBox();
            if (previousScreen instanceof RenderableModule.ModuleOptionsScreen) ((RenderableModule.ModuleOptionsScreen) previousScreen).drawModule();
        }

        @Override
        protected void actionPerformed(GuiButton button) throws IOException {
            super.actionPerformed(button);
            if (button.id == 1) Minecraft.getMinecraft().displayGuiScreen(previousScreen);
        }

        protected void keyTyped(char typedChar, int keyCode) throws IOException
        {
            super.keyTyped(typedChar, keyCode);
            this.textField.textboxKeyTyped(typedChar, keyCode);

            if (keyCode == 15)
            {
                this.textField.setFocused(!this.textField.isFocused());
            }

            if (keyCode == 28 || keyCode == 156)
            {
                this.actionPerformed(this.buttonList.get(0));
            }



            String hexa =  this.textField.getText();
            setColor(hexa);


            //TODO: apply hexa
        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            this.textField.mouseClicked(mouseX, mouseY, mouseButton);
            if (red.dragging || green.dragging || blue.dragging || alpha.dragging) updateColor();
        }

        @Override
        protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
            super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            if (red.dragging || green.dragging || blue.dragging || alpha.dragging) updateColor();
        }

        @Override
        protected void mouseReleased(int mouseX, int mouseY, int state) {
            super.mouseReleased(mouseX, mouseY, state);
            if (red.dragging || green.dragging || blue.dragging || alpha.dragging) updateColor();
        }

        private void updateColor() {
            color.setRed(red.toInt());
            color.setGreen(green.toInt());
            color.setBlue(blue.toInt());
            color.setAlpha(alpha.toInt());
            String hex = String.format("#%02x%02x%02x%02x",red.toInt(), green.toInt(), blue.toInt(), alpha.toInt());
            this.textField.setText(hex);
        }

        private void setColor(String hexa) {
            hexa = hexa.replace("#", "");
            Color color = new Color(255, 255, 255);
            switch (hexa.length()) {

                case 2:
                    color = new Color(Integer.valueOf(hexa.substring(0, 2), 16), 0, 0);
                    break;
                case 4:
                    color = new Color(Integer.valueOf(hexa.substring(0, 2), 16),Integer.valueOf(hexa.substring(2, 4),  16), 0);
                    break;
                case 6:
                    color = new Color(Integer.valueOf(hexa.substring(0, 2), 16),Integer.valueOf(hexa.substring(2, 4), 16),Integer.valueOf(hexa.substring(4, 6), 16));
                    break;
                case 8:
                    color = new Color(Integer.valueOf(hexa.substring(0, 2), 16),Integer.valueOf(hexa.substring(2, 4), 16),Integer.valueOf(hexa.substring(4, 6), 16),Integer.valueOf(hexa.substring(6, 8), 16));
                    break;
            }
            ColorChooser.this.color.setRed(color.getRed());
            ColorChooser.this.color.setGreen(color.getGreen());
            ColorChooser.this.color.setBlue(color.getBlue());
            ColorChooser.this.color.setAlpha(color.getAlpha());
            red.intToSliderValue(color.getRed());
            green.intToSliderValue(color.getGreen());
            blue.intToSliderValue(color.getBlue());
            alpha.intToSliderValue(color.getAlpha());
        }

        @Override
        public void onGuiClosed() {
            super.onGuiClosed();
            NakoryClient.getInstance().getHudPropertyApi().getHandlers().forEach(renderer -> {
                if (renderer instanceof RenderableModule) ((RenderableModule) renderer).saveOptions();
            });
        }
    }

    @Override
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        super.drawString(fontRendererIn, text, x, y, this.color.toRGBA());
    }

    public CustomizableColor getColor() {
        return color;
    }
}
