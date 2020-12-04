package com.nakory.hud;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;
import com.nakory.event.EventHandler;
import com.nakory.event.implementations.RenderEvent;
import com.nakory.hud.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
public final class HudPropertyApi {

	public static HudPropertyApi newInstance(){
		HudPropertyApi api = new HudPropertyApi();
		return api;
	}

	private Map<Class, IRenderer> registeredRenderers = new HashMap<>();
	private Collection<IRenderer> renderers = registeredRenderers.values();
	private Minecraft mc = Minecraft.getMinecraft();

	private boolean renderOutlines = true;

	private HudPropertyApi(){}

	public void register(IRenderer... renderers){
		for(IRenderer renderer : renderers){
			this.registeredRenderers.put(renderer.getClass(), renderer);
		}
	}

	public void unregister(IRenderer... renderers){
		for(IRenderer renderer : renderers){
			this.registeredRenderers.remove(renderer);
		}
	}

	public Collection<IRenderer> getHandlers(){
		return Sets.newHashSet(renderers);
	}

	public boolean getRenderOutlines(){
		return renderOutlines;
	}

	public void setRenderOutlines(boolean renderOutlines){
		this.renderOutlines = renderOutlines;
	}

	public void openConfigScreen(){
		mc.displayGuiScreen(new PropertyScreen(this));
	}

	@EventHandler
	public void onRender(RenderEvent event){
		if(mc.currentScreen == null && !(mc.currentScreen instanceof PropertyScreen)){
			renderers.forEach(this::callRenderer);
		}
	}

	private void callRenderer(IRenderer renderer){
		if(!renderer.isEnabled()){
			return;
		}
		
		ScreenPosition position = renderer.load();

		if(position == null){
			position = ScreenPosition.fromRelativePosition(0.5, 0.5);
		}

		renderer.render(position);
	}
	
	public <T> Optional<T> getRendererByInstance(Class<T> aClass) {
		return (Optional<T>) Optional.ofNullable(registeredRenderers.get(aClass));
	}
}
