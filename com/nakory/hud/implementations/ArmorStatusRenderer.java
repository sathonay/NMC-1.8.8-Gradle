package com.nakory.hud.implementations;

import org.lwjgl.opengl.GL11;

import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ArmorStatusRenderer implements IRenderer{

	private FontRenderer fontRenderer;
	
	public ArmorStatusRenderer() {
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
		
		ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
		if (heldItem != null) renderItem(position, validIndex++, heldItem);
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.inventory.armorInventory.length; i++) {
			ItemStack item = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[i];
			if (item != null) renderItem(position, validIndex++, item);
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
		renderItem(position, 4, new ItemStack(Items.diamond_helmet));
		renderItem(position, 3, new ItemStack(Items.diamond_chestplate));
		renderItem(position, 2, new ItemStack(Items.diamond_leggings));
		renderItem(position, 1, new ItemStack(Items.diamond_boots));	
		renderItem(position, 0, new ItemStack(Items.diamond_sword));	
	}
	
	private void renderItem(ScreenPosition pos, int index, ItemStack item) {
		if (item == null) {
			return;
		}

		
		int yOffSet = (-16 * index) + 64;
		
		if (item.getItem().isDamageable()) {
			int damage = item.getMaxDamage() - item.getItemDamage();
			Minecraft.getMinecraft().fontRendererObj.drawString(damage + "/" + item.getMaxDamage(), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yOffSet + 5, -1);
		}
		GL11.glPushMatrix();
		
		RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, pos.getAbsoluteX(), pos.getAbsoluteY() + yOffSet);
		GL11.glPopMatrix();
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