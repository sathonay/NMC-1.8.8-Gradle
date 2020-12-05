package com.nakory;

import com.nakory.event.EventHandler;
import com.nakory.event.EventManager;
import com.nakory.event.Listener;
import com.nakory.event.implementations.ClientTickEvent;
import com.nakory.gui.NakorySettings;
import com.nakory.hud.HudPropertyApi;
import com.nakory.modules.implementations.ArmorStatusModule;
import com.nakory.modules.implementations.EffectsStatusModule;
import com.nakory.modules.implementations.FPSRModule;
import com.nakory.modules.implementations.togglesprint.ToggleSprintModule;
import com.nakory.modules.implementations.CPSModule;

import net.minecraft.client.Minecraft;

public class NakoryClient {

	private static final NakoryClient INSTANCE = new NakoryClient();
	
	public static NakoryClient getInstance() {
		return INSTANCE;
	}
	
	private HudPropertyApi hudPropertyApi = HudPropertyApi.newInstance();
	private FileManager fileManager = new FileManager();
	private NakorySettings settings;
	
	public void init() {
		fileManager.init();
		settings = NakorySettings.load();
		EventManager.register(this);
		EventManager.register(hudPropertyApi);
	}
	
	public void start() {
		registerHUD();
	}
	
	private void registerHUD() {
		hudPropertyApi.register(new FPSRModule());
		hudPropertyApi.register(new ArmorStatusModule());
		hudPropertyApi.register(new EffectsStatusModule());
		hudPropertyApi.register(new ToggleSprintModule());
		hudPropertyApi.register(new CPSModule());
	}
	
	@EventHandler
	public void onTick(ClientTickEvent event) {
		if (Minecraft.getMinecraft().gameSettings.clientHUDProperty.isPressed()) {
			hudPropertyApi.openConfigScreen();
		}
	}
	
	public HudPropertyApi getHudPropertyApi() {
		return hudPropertyApi;
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
	public NakorySettings getSettings() {
		return settings;
	}
	
	public void shutdown() {}
}
