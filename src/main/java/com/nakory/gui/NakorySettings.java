package com.nakory.gui;

import java.io.File;
import java.util.Optional;

import com.nakory.NakoryClient;

public class NakorySettings {
	public boolean showRedNumberInScoreboard = true;
	public boolean renderChatBackground = true;
	public boolean renderChatBackgroundToggle = true;

	public boolean toggleSneak = true;
	public boolean toggleSprint = true;
	public boolean toggleFlyingBoost = true;
	public boolean renderMenuBackground = true;
	
	
	public static NakorySettings load() {
		Optional<NakorySettings> settings = NakoryClient.getInstance().getFileManager().readFromJson(new File(NakoryClient.getInstance().getFileManager().getRootDirectory(), "settings.json"), NakorySettings.class);
		if (settings.isPresent()) return settings.get();
		return new NakorySettings();
	}
	
	public void save() {
		NakoryClient.getInstance().getFileManager().writJsonToFile(new File(NakoryClient.getInstance().getFileManager().getRootDirectory(), "settings.json"), this);
	}
}
