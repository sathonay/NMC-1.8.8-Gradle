package com.nakory;

import com.nakory.event.EventHandler;
import com.nakory.event.EventManager;
import com.nakory.event.Listener;
import com.nakory.event.implementations.ClientTickEvent;
import com.nakory.hud.HudPropertyApi;
import com.nakory.hud.implementations.FPSRenderer;
import com.nakory.hud.test.TestRenderer;

import net.minecraft.client.Minecraft;

public class NakoryClient {

	private static final NakoryClient INSTANCE = new NakoryClient();
	
	public static NakoryClient getInstance() {
		return INSTANCE;
	}
	
	private HudPropertyApi hudPropertyApi = HudPropertyApi.newInstance();
	
	public void init() {
		EventManager.register(this);
		EventManager.register(hudPropertyApi);
		hudPropertyApi.register(new FPSRenderer());
	}
	
	@EventHandler
	public void onTick(ClientTickEvent event) {
		if (Minecraft.getMinecraft().gameSettings.clientHUDProperty.isPressed()) {
			hudPropertyApi.openConfigScreen();
		}
	}
	
	public void shutdown() {
		
	}
}