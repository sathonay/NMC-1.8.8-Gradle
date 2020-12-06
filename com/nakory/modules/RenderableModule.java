package com.nakory.modules;

import java.io.File;
import java.util.Optional;

import com.nakory.NakoryClient;
import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;

public abstract class RenderableModule implements IRenderer{

	protected ScreenPosition position;
	
	public RenderableModule() {
		position = loadPositionFromFile();
	}
	
	public ScreenPosition getDefaultPosition() {
		return ScreenPosition.fromRelativePosition(0.5,  0.5);
	}
	
	private ScreenPosition loadPositionFromFile() {
		Optional<ScreenPosition> position = NakoryClient.getInstance().getFileManager().readFromJson(new File(getFolder(), "position.json"), ScreenPosition.class);
		if (position.isPresent()) return position.get();
		
		ScreenPosition defaultPosition = getDefaultPosition();
		save(defaultPosition);
		return defaultPosition;
	}
	
	
	private File folder;
	
	private File getFolder() {
		if (this.folder == null) this.folder = new File(NakoryClient.getInstance().getFileManager().getModulesDirectory(), this.getClass().getSimpleName().toLowerCase());
		this.folder.mkdirs();
		return this.folder;
	}
	
	@Override
	public ScreenPosition load() {
		return loadPositionFromFile();
	}
	
	@Override
	public void save(ScreenPosition pos) {
		this.position = pos;
		savePositionToFile();	
	}
	
	private void savePositionToFile() {
		NakoryClient.getInstance().getFileManager().writJsonToFile(new File(getFolder(), "position.json"), position);
	}
	
	private boolean rendererEnabled = loadRendererEnabled();
	
	private boolean loadRendererEnabled() {
		Optional<Boolean> rendererEnabled = NakoryClient.getInstance().getFileManager().readFromJson(new File(getFolder(), "rendererEnabled.json"), Boolean.class);
		if (rendererEnabled.isPresent()) return rendererEnabled.get();
		return true;
	}
	
	private void saveRendererEnabledToFile() {
		NakoryClient.getInstance().getFileManager().writJsonToFile(new File(getFolder(), "rendererEnabled.json"), rendererEnabled);
	}
	
	@Override
	public void setEnable(boolean enable) {
		this.rendererEnabled = enable;
		saveRendererEnabledToFile();
	}

	/**
	 * For renderer
	 */
	@Override
	public boolean isEnabled() {
		return rendererEnabled;
	}
	
}
