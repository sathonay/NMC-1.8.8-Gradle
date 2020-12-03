package com.nakory.hud;

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

	public PropertyScreen(HudPropertyApi api){
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
	
	private int xDis, yDis;

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		super.drawDefaultBackground();

		float zBackup = this.zLevel;
		this.zLevel = 200;	

		renderers.forEach((renderer, position) -> renderer.renderDummy(position));

		this.zLevel = zBackup;
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
			System.out.println(button);
			if (button == 0) {
				ScreenPosition position = renderers.get(renderer);
				xDis = x - position.getAbsoluteX();
				yDis = y - position.getAbsoluteY();
			} else if (button == 1) renderer.setEnable(!renderer.isEnabled());
		});
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if(selectedRenderer.isPresent() && button == 0){
			moveSelectedRendererBy(x - xDis, y - yDis);
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