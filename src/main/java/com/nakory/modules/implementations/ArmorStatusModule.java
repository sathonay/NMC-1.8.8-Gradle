package com.nakory.modules.implementations;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import com.nakory.hud.IRenderer;
import com.nakory.hud.PropertyScreen;
import com.nakory.hud.util.ScreenPosition;
import com.nakory.modules.RenderableModule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ArmorStatusModule extends RenderableModule{

	private int witdth = 64;
	private FontRenderer fontRenderer;
	
	public ArmorStatusModule() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	}

	@Override
	public ScreenPosition getDefaultPosition() {
		// TODO Auto-generated method stub
		return ScreenPosition.fromRelativePosition(0, 0);
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
		return preCalcHeight();
	}

	@Override
	public int getWidth() {
		return preCalcWidth();
	}
	
	private final ItemStack[] dummyArmor = {new ItemStack(Items.wooden_sword), new ItemStack(Items.leather_boots), new ItemStack(Items.leather_leggings), new ItemStack(Items.leather_chestplate), new ItemStack(Items.leather_helmet)};

	@Override
	public void renderDummy(ScreenPosition position) {
		for (int index = 0; index < dummyArmor.length; index++) {
			renderItem(position, index, dummyArmor[index]);
		}
	}
	
	private int preCalcHeight() {
		return Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? getHeight(dummyArmor.length) : getHeight(ArrayUtils.add(Minecraft.getMinecraft().thePlayer.inventory.armorInventory, Minecraft.getMinecraft().thePlayer.getHeldItem()).length);  
	}
	
	private int getHeight(int items) {
		return 16 * items;
	}
	
	private int preCalcWidth() {
		return Minecraft.getMinecraft().currentScreen instanceof PropertyScreen ? getWidth(new ItemStack(Items.wooden_sword)) : getWidth(ArrayUtils.add(Minecraft.getMinecraft().thePlayer.inventory.armorInventory, Minecraft.getMinecraft().thePlayer.getHeldItem()));  
	}
	
	private int getWidth(ItemStack... items) {
		int maxWidth = 16;
		for (ItemStack item : items) {
			if (item == null) continue;
			if (item.getItem().isDamageable()) {
				int damage = item.getMaxDamage() - item.getItemDamage();
				int width = 20 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(damage + "/" + item.getMaxDamage());
				if (width > maxWidth) maxWidth = width;
			}
		}
		
		return maxWidth;
	}
	
	private void renderItem(ScreenPosition pos, int index, ItemStack item) {
		if (item == null) {
			return;
		}

		int yOffSet = (-16 * index) + 64;
		
		if (item.getItem().isDamageable()) {
			int stringWidth = 20;
			int damage = item.getMaxDamage() - item.getItemDamage();
			Minecraft.getMinecraft().fontRendererObj.drawString(damage + "/" + item.getMaxDamage(), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yOffSet + 5, -1);
		}
		GL11.glPushMatrix();
		
		RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, pos.getAbsoluteX(), pos.getAbsoluteY() + yOffSet);
		GL11.glPopMatrix();
	}
}