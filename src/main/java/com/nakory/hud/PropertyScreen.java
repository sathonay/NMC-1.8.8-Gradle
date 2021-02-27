package com.nakory.hud;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;

import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class PropertyScreen extends GuiScreen {

	private Minecraft mc = Minecraft.getMinecraft();

	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();
	private Optional<IRenderer> selectedRenderer = Optional.empty(); 

	private boolean renderOutlines;

	private int prevX, prevY;

	private final HudPropertyApi api;
	
	public PropertyScreen(HudPropertyApi api){
		this.api = api;
	}
	
	private int deltaX, deltaY;

	
	private float zBackup = 0;
	
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		Collection<IRenderer> registeredRenderers = api.getHandlers();

		for(IRenderer ren : registeredRenderers){ 
			/*if(!ren.isEnabled()){
				continue;
			}*/
			
			ScreenPosition pos = ren.load();

			if(pos == null){
				pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
			}

			adjustBounds(ren, pos);	

			this.renderers.put(ren, pos);
		}

		this.renderOutlines = api.getRenderOutlines();
	}
	
	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawDefaultBackground();

		float zBackup = this.zLevel;
		this.zLevel = 200;	

		renderers.forEach((renderer, position) -> {
			this.drawHollowRect(position.getAbsoluteX(), position.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), renderer.isEnabled() ? new Color(0, 155, 0).getRGB() : new Color(155, 0, 0).getRGB());
			renderer.renderDummy(position);
			});

		this.zLevel = zBackup;
	}
	
	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x - 1, x + w, y - 1, color);
		this.drawHorizontalLine(x - 1, x + w, y + h, color);
		this.drawVerticalLine(x - 1, y + h, y - 1, color);
		this.drawVerticalLine(x + w, y + h, y - 1, color);
		this.drawRect(x, y, x + w, y + h, new Color(144, 144, 144, 100).getRGB());
	}


	@Override
	protected void keyTyped(char c, int key) {
		if (key == 1) {
			renderers.entrySet().forEach((entry) -> { // Save all entries
				entry.getKey().save(entry.getValue());	
			});
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		loadMouseOver(x, y);
		
		selectedRenderer.ifPresent(renderer -> {
			if (button == 0) {
				ScreenPosition position = renderers.get(renderer);
				deltaX = x - position.getAbsoluteX();
				deltaY = y - position.getAbsoluteY();
			} else if (button == 1) renderer.setEnable(!renderer.isEnabled());
		});
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if(selectedRenderer.isPresent() && button == 0){
			moveSelectedRendererBy(x - deltaX, y - deltaY);
		}
	}
	
	private void moveSelectedRendererBy(int offsetX, int offsetY){
		IRenderer renderer = selectedRenderer.get();
		ScreenPosition position = renderers.get(renderer);

		position.setAbsolute(offsetX, offsetY);

		adjustBounds(renderer, position);
	}

	@Override
	public void onGuiClosed() {
		renderers.forEach(IRenderer::save);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	private void adjustBounds(IRenderer renderer, ScreenPosition pos){
		ScaledResolution res = new ScaledResolution(mc);

		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();

		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY);
	}

	private void loadMouseOver(int x, int y){
		this.selectedRenderer = renderers.keySet().stream()
				.filter(new MouseOverFinder(x, y))
				.findFirst();
	}

	private class MouseOverFinder implements Predicate<IRenderer>{

		private int mouseX, mouseY;

		public MouseOverFinder(int mouseX, int mouseY) {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
		}

		@Override
		public boolean test(IRenderer renderer) {
			ScreenPosition pos = renderers.get(renderer);

			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();

			if(mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()){
				if(mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()){
					return true;
				}
			}

			return false;
		}

	}
}
