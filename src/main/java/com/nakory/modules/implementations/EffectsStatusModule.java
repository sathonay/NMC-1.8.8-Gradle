package com.nakory.modules.implementations;

import java.util.Map;

import com.nakory.gui.button.ColorChooser;
import com.nakory.gui.button.CustomizableColor;

import com.nakory.hud.PropertyScreen;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EffectsStatusModule extends RenderableModule{

	private FontRenderer fontRenderer;
	
	public EffectsStatusModule() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;

	}

	@Override
	public Map<String, ColorChooser> getDefaultOptions() {
		Map<String, ColorChooser> options = super.getDefaultOptions();
		options.put("time_color", new ColorChooser("Time Color", new CustomizableColor(128, 128, 128)));
		return options;
	}
	
	@Override
	public ScreenPosition getDefaultPosition() {
		// TODO Auto-generated method stub
		return ScreenPosition.fromRelativePosition(0.85, 0);
	}

	@Override
	public void render(ScreenPosition position) {
		int validIndex = 0;
		for(PotionEffect effect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
			renderEffect(position, validIndex++, effect);
		}
	}

	@Override
	public int getHeight() {
		return Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? getHeight(1) : getHeight(Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size());
	}

	@Override
	public int getWidth() {
		return preCalcWidth();
	}
	
	private int preCalcWidth() {
		return Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? getWidth(new PotionEffect(3, 0, 0)) : getWidth(Minecraft.getMinecraft().thePlayer.getActivePotionEffects().toArray(new PotionEffect[0]));
	}
	
	private int getHeight(int effects) {
		return effects * 20 - 2;
	}
	
	private int getWidth(PotionEffect... effects) {
		int maxWidth = 0;
		
		for (PotionEffect effect : effects) {
			int width = 0;
			Potion potion = Potion.potionTypes[effect.getPotionID()];
			String s1 = I18n.format(potion.getName(), new Object[0]);

	        if (effect.getAmplifier() == 1)
	        {
	            s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
	        }
	        else if (effect.getAmplifier() == 2)
	        {
	            s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
	        }
	        else if (effect.getAmplifier() == 3)
	        {
	            s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
	        }

	        width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s1);
	        int timeWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(Potion.getDurationString(effect));
	        if (timeWidth > width) width = timeWidth;
	        width += 20;
	        if (width > maxWidth) maxWidth = width;
		
		}
		
		return maxWidth;
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		renderEffect(position, 0, new PotionEffect(3, 0, 0));
	}
	
	private static final ResourceLocation potionInventory = new ResourceLocation("textures/gui/container/inventory.png");
	
	private void renderEffect(ScreenPosition pos, int index, PotionEffect effect) {
	
		int yOffSet = index * 20;
		
		Gui gui = Minecraft.getMinecraft().currentScreen;
		if (gui == null) gui = Minecraft.getMinecraft().ingameGUI;
		
		Potion potion = Potion.potionTypes[effect.getPotionID()];
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(potionInventory);

		String s1 = I18n.format(potion.getName(), new Object[0]);

		if (effect.getAmplifier() == 1)
		{
			s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
		}
		else if (effect.getAmplifier() == 2)
		{
			s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
		}
		else if (effect.getAmplifier() == 3)
		{
			s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
		}

		if (potion.hasStatusIcon())
        {
            int potionIndex = potion.getStatusIconIndex();

			GlStateManager.color(255, 255, 255, 255);
            gui.drawTexturedModalRect(pos.getAbsoluteX(), pos.getAbsoluteY() + yOffSet, 0 + potionIndex % 8 * 18, 198 + potionIndex / 8 * 18, 18, 18);
        }

        Minecraft.getMinecraft().fontRendererObj.drawString(s1, (pos.getAbsoluteX() + 20), (pos.getAbsoluteY() + yOffSet + 1), options.get("color").getColor().toRGBA());
        Minecraft.getMinecraft().fontRendererObj.drawString(Potion.getDurationString(effect), (pos.getAbsoluteX() + 20), (pos.getAbsoluteY() + yOffSet + 10), options.get("time_color").getColor().toRGBA());
	}
}