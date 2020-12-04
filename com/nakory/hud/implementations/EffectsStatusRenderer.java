package com.nakory.hud.implementations;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EffectsStatusRenderer implements IRenderer{

	private FontRenderer fontRenderer;
	
	public EffectsStatusRenderer() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}
	
	ScreenPosition pos;
	
	@Override
	public void save(ScreenPosition position) {
		pos = position;
	}

	@Override
	public ScreenPosition load() {
		return pos;
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
		return 64 + 16;
	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		renderEffect(position, 0, new PotionEffect(1, 0, 0));
		renderEffect(position, 1, new PotionEffect(2, 0, 0));
		renderEffect(position, 2, new PotionEffect(3, 0, 0));
		renderEffect(position, 3, new PotionEffect(4, 0, 0));
	}
	
	private static final ResourceLocation potionInventory = new ResourceLocation("textures/gui/container/inventory.png");
	
	private void renderEffect(ScreenPosition pos, int index, PotionEffect effect) {
	
		int yOffSet = index * 20;
		
		Gui gui = Minecraft.getMinecraft().currentScreen;
		if (gui == null) gui = Minecraft.getMinecraft().ingameGUI;
		
		Potion potion = Potion.potionTypes[effect.getPotionID()];
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(potionInventory);

        if (potion.hasStatusIcon())
        {
            int potionIndex = potion.getStatusIconIndex();
            gui.drawTexturedModalRect(pos.getAbsoluteX(), pos.getAbsoluteY() + yOffSet, 0 + potionIndex % 8 * 18, 198 + potionIndex / 8 * 18, 18, 18);
        }

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

        Minecraft.getMinecraft().fontRendererObj.drawString(s1, (pos.getAbsoluteX() + 20), (pos.getAbsoluteY() + yOffSet + 1), 16777215);
        Minecraft.getMinecraft().fontRendererObj.drawString(Potion.getDurationString(effect), (pos.getAbsoluteX() + 20), (pos.getAbsoluteY() + yOffSet + 10), 8355711);
	}
	
	
	private boolean enable = true;
	
	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	@Override
	public boolean isEnabled() {
		return enable;
	}
}