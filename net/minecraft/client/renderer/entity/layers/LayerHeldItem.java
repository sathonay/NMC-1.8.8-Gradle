package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer<EntityLivingBase>
{
    private final RendererLivingEntity<?> livingEntityRenderer;

    public LayerHeldItem(RendererLivingEntity<?> livingEntityRendererIn)
    {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
    {
        ItemStack itemstack = entitylivingbaseIn.getHeldItem();

        if (itemstack != null)
        {
            GlStateManager.pushMatrix();

            if (this.livingEntityRenderer.getMainModel().isChild)
            {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(f, f, f);
            }

            ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
            GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);

            if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer)entitylivingbaseIn).fishEntity != null)
            {
                itemstack = new ItemStack(Items.fishing_rod, 0);
            }

            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getMinecraft();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2)
            {
                GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                float f1 = 0.375F;
                GlStateManager.scale(-f1, -f1, f1);
            }

            if (entitylivingbaseIn.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.203125F, 0.0F);
            }
            
            // NMC-1.8.8 - old sword helding & blocking animation like 1.7
            if (entitylivingbaseIn instanceof AbstractClientPlayer) {
            	AbstractClientPlayer player = ((AbstractClientPlayer)entitylivingbaseIn);
            	
            	if (item.getItemUseAction(itemstack) == EnumAction.BLOCK) {
            		//NMC-1.8.8 - Check if the player is using the item
            		if (player.getItemInUseCount() > 0) {
                		//NMC-1.8.8 - Old blocking animation
            			GlStateManager.translate(0.025F, 0.1F, -0.0F);
                		GlStateManager.rotate(-35.0F, 0.0F, 1.0F, 0.0F);
                		GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
                		GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
            		} else {
                		//NMC-1.8.8 - Old helding
            			GlStateManager.translate(0.0F, 0.05F, 0.1F);
                		GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
                		GlStateManager.rotate(-15.50F, 1.0F, 0.0F, 0.0F);
                		GlStateManager.rotate(0.0F, 0.0F, 0.0F, 1.0F);
            		}
            	}
            }

            minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
