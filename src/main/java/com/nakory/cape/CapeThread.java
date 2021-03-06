package com.nakory.cape;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FilenameUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import optifine.CapeUtils;

public class CapeThread extends Thread {
	/*
	private static CapeThread thread;
	
	private final Map<String, AbstractClientPlayer> MAP;
	
	public CapeThread(String username, AbstractClientPlayer player) {
		if (thread != null) {
			this.MAP = null;
			thread.MAP.put(username, player);
			return;
        }
		this.MAP = new ConcurrentHashMap<String, AbstractClientPlayer>();
		this.MAP.put(username, player);
		thread = this;
		thread.start();
	}
	
	@Override
	public void run() {
		super.run();
		for (Entry<String, AbstractClientPlayer> entry : this.MAP.entrySet()) {
			final String username = entry.getKey();
			final AbstractClientPlayer player = entry.getValue();
			String ofCapeUrl = "http://nakory.online/capes/" + username + ".png";
	        try {
	        	if(!doesURLExist(ofCapeUrl)) ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
			} catch (IOException e) {
	            ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
			}
	        
	        String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
	        final ResourceLocation resourceLocation = new ResourceLocation("capeof/" + mptHash);
	        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
	        ITextureObject tex = textureManager.getTexture(resourceLocation);
	        
	        IImageBuffer iib = new CapeImageBuffer(player, resourceLocation);
	        
	        ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, ofCapeUrl, (ResourceLocation)null, iib);
	        textureCape.pipeline = true;
	        textureManager.loadTexture(resourceLocation, textureCape);
		}
		thread = null;
        this.stop();
	}
	*/
	
	private String username;
	private WeakReference<AbstractClientPlayer> playerRef;
	
	public CapeThread(String username, AbstractClientPlayer abstractClientPlayer) {
		this.username = username;
		this.playerRef = new WeakReference<AbstractClientPlayer>(abstractClientPlayer);
		start();
	}
	
	@Override
	public void run() {
		super.run();
		
		String capeUrl = "http://nakory.com/capes/" + username + ".png";
	    try {
	       	if(!doesURLExist(capeUrl)) capeUrl = "http://s.optifine.net/capes/" + username + ".png";
		} catch (IOException e) {
			capeUrl = "http://s.optifine.net/capes/" + username + ".png";
		}
	        
        String mptHash = FilenameUtils.getBaseName(capeUrl);
	    final ResourceLocation resourceLocation = new ResourceLocation("capeof/" + mptHash);
	    TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
	    ITextureObject tex = textureManager.getTexture(resourceLocation);
	    
	    AbstractClientPlayer player = this.playerRef.get();
	    
	    if(player == null) {
	    	return;
	    }
	    
	    IImageBuffer iib = new CapeImageBuffer(player, resourceLocation);
	    ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, capeUrl, (ResourceLocation)null, iib);
	    textureCape.pipeline = true;
	    textureManager.loadTexture(resourceLocation, textureCape);
	}

	private static boolean doesURLExist(String urlStr) throws IOException
    {

		URL url = new URL(urlStr);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("HEAD");
		boolean valid = huc.getResponseCode() == HttpURLConnection.HTTP_OK;
		huc.disconnect();
		return valid;
    }
}
