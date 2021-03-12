package com.nakory.modules;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nakory.NakoryClient;
import com.nakory.gui.button.ColorChooser;
import com.nakory.gui.button.CustomizableColor;
import com.nakory.hud.IRenderer;
import com.nakory.hud.util.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class RenderableModule implements IRenderer{

	protected ScreenPosition position;
	public Map<String, ColorChooser> options = loadOptions();

	protected static final ResourceLocation check  = new ResourceLocation("nakory/icons/check.png");
	protected static final ResourceLocation cross  = new ResourceLocation("nakory/icons/cross.png");
	protected static final ResourceLocation wheel  = new ResourceLocation("nakory/icons/wheel.png");
	
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
	
	protected File getFolder() {
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
		saveOptions();
	}

	public Map<String, ColorChooser> loadOptions() {
		Optional<String> json = NakoryClient.getInstance().getFileManager().readFromFile(new File(getFolder(), "options.json"));
		if (json.isPresent()) {
			System.out.println(json.get());
			Map<String, Object> map = parse(json.get());
			if (map.isEmpty()) {
				return getDefaultOptions();
			}

			Map<String, ColorChooser> options = new HashMap<>();

			for (String key : map.keySet()) {
				if (map.get(key) instanceof Map) {
					Map<Object, Object> valMap = (Map<Object, Object>) map.get(key);
					if (valMap.containsKey("color")) {
						Map<Object, Object> customizableColor = (Map<Object, Object>) valMap.get("color");
						options.put(key, new ColorChooser((String) valMap.get("displayString"), new CustomizableColor(Integer.parseInt(String.valueOf(customizableColor.get("red"))), Integer.parseInt(String.valueOf(customizableColor.get("green"))), Integer.parseInt(String.valueOf(customizableColor.get("blue"))), Integer.parseInt(String.valueOf(customizableColor.get("alpha"))))));
					}
				}
			}
			return options;
		}

		return getDefaultOptions();
	}

	public Map<String, ColorChooser> getDefaultOptions() {
		Map<String, ColorChooser> defaultMap = new HashMap<>();
		defaultMap.put("color", new ColorChooser("Text Color", new CustomizableColor()));
		defaultMap.put("background", new ColorChooser("Background Color", new CustomizableColor(0, 0, 0, 102)));
		return defaultMap;
	}

	protected int backgroundXOffSet, backgroundYOffSet;

	public int getBackgroundXOffSet() {
		return backgroundXOffSet;
	}

	public int getBackgroundYOffSet() {
		return backgroundYOffSet;
	}

	public void setBackgroundOffSet(int x, int y) {
		this.backgroundXOffSet = x;
		this.backgroundYOffSet = y;
	}

	public void drawBackground(int x, int y, int w, int h) {
		GuiScreen.drawRect(x + backgroundXOffSet, y + backgroundYOffSet, x + w, y + h, options.get("background").getColor().toRGBA());
	}


	public static HashMap<String, Object> parse(String json) {
		JsonObject object = (JsonObject) new JsonParser().parse(json);
		Set<Map.Entry<String, JsonElement>> set = object.entrySet();
		Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
		HashMap<String, Object> map = new HashMap<String, Object>();
		while (iterator.hasNext()) {
			Map.Entry<String, JsonElement> entry = iterator.next();
			String key = entry.getKey();
			JsonElement value = entry.getValue();
			if (!value.isJsonPrimitive()) {
				map.put(key, parse(value.toString()));
			} else {
				map.put(key, value.getAsString());
			}
		}
		return map;
	}

	public void saveOptions() {
		NakoryClient.getInstance().getFileManager().writJsonToFile(new File(getFolder(), "options.json"), options);
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

	public void afterRender(ScreenPosition position) {
		GlStateManager.color(255, 255, 255, 255);
		Minecraft.getMinecraft().getTextureManager().bindTexture(isEnabled() ? check : cross);
		Minecraft.getMinecraft().currentScreen.drawModalRectWithCustomSizedTexture(position.getAbsoluteX(), position.getAbsoluteY() + getHeight() - 8, 300, 0.0F, 0.0F, 8, 8, 8, 8);
		Minecraft.getMinecraft().getTextureManager().bindTexture(wheel);
		Minecraft.getMinecraft().currentScreen.drawModalRectWithCustomSizedTexture(position.getAbsoluteX() + getWidth() - 8, position.getAbsoluteY() + getHeight() - 8, 300, 0.0F, 0.0F, 8, 8, 8, 8);
	}

	public void openOptions() {
		Minecraft.getMinecraft().displayGuiScreen(new ModuleOptionsScreen());
	}

	public class ModuleOptionsScreen extends GuiScreen {

		private GuiScreen previousScreen;

		public ModuleOptionsScreen() {
			previousScreen = Minecraft.getMinecraft().currentScreen;
		}

		@Override
		public void initGui() {
			super.initGui();
			options.values().forEach(colorChooser -> this.buttonList.add(colorChooser));
			buttonList.add(new GuiButton(1, 0, 0, "Done"));
			int height = 0;
			for (int i = buttonList.size() - 1; i >= 0; i--) {
				GuiButton button = buttonList.get(i);
				button.xPosition = (this.width - button.getButtonWidth()) / 2;
				button.yPosition = this.height / 2 - height - button.getButtonHeight();
				height += button.getButtonHeight() + 4;
			}
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			super.drawScreen(mouseX, mouseY, partialTicks);
			drawModule();
		}

		public void drawModule() {
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			if (screen == null) return;
			renderDummy(ScreenPosition.fromAbsolutePosition(screen.width / 6 - getWidth() / 2, screen.height / 2 - getHeight()));
		}

		@Override
		protected void actionPerformed(GuiButton button) throws IOException {
			super.actionPerformed(button);
			if (button.id == 1) Minecraft.getMinecraft().displayGuiScreen(previousScreen);
		}
	}
}
