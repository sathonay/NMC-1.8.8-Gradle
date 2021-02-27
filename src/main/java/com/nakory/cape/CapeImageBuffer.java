package com.nakory.cape;

import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;
import optifine.CapeUtils;

public class CapeImageBuffer implements IImageBuffer {
	ImageBufferDownload imageBufferDownload;
	
	
	public final WeakReference<AbstractClientPlayer> playerRef;
	public final ResourceLocation resourceLocation;
	
	public CapeImageBuffer(AbstractClientPlayer player, ResourceLocation resourceLocation) {
		playerRef = new WeakReference<AbstractClientPlayer>(player);
		this.resourceLocation = resourceLocation;
		imageBufferDownload = new ImageBufferDownload();
	}
	
	private boolean aplicate = false;
	
    public BufferedImage parseUserSkin(BufferedImage var1) {
        return CapeUtils.parseCape(var1);
    }

	@Override
	public void skinAvailable() {
		AbstractClientPlayer player = playerRef.get();
		if (player != null) {
			player.setLocationOfCape(resourceLocation);
		}
	}
}
