package com.nakory.hud.implementations.toggleSprint;

import java.util.Optional;

import com.ibm.icu.text.DecimalFormat;
import com.nakory.NakoryClient;
import com.nakory.gui.GuiClientOptions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;

public class ToggleSprintMovementInput extends MovementInput {
	
	
	
	private boolean sprint = false;
	private GameSettings gameSettings;
	private int sneakWasPressed = 0;
	private int sprintWasPressed = 0;
	private EntityPlayerSP player;
	private float originalFlySpeed = -1.0F;
	private float boostedFlySpeed = 0;
	
	
	public ToggleSprintMovementInput(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}
	
	//private static final DecimalFormat df = new DecimalFormat("#.0");
	
	public String getDisplayText() {
		
		String displayText = "";
		
		boolean isFlying = Minecraft.getMinecraft().thePlayer.capabilities.isFlying;
		boolean isRiding = Minecraft.getMinecraft().thePlayer.isRiding();
		boolean isHoldingSneak = gameSettings.keyBindSneak.isKeyDown();
		boolean isHoldingSprint = gameSettings.keyBindSprint.isKeyDown();
		
		if (isFlying) {
			if (boostedFlySpeed > 0.0F) displayText += "[Flying (" + /*df.format(*/boostedFlySpeed / originalFlySpeed/*)*/ + "x Boost)]  ";
			else displayText = "[Flying]  ";
		}
		
		if (isRiding) {
			displayText += "[Riding]  ";
		}
		
		if (sneak) {
			if (isFlying) displayText +="[Descending]";
			else if (isRiding) displayText += "[Dismounting]";
			else if(isHoldingSneak) displayText += "[Sneaking (Key Held)]";
			else displayText += "[Sneaking (Key Toggled)]";
		} else if (sprint && !isFlying && !isRiding) {
			if(isHoldingSprint) displayText += "[Sprinting (Key Held)]";
			else displayText += "[Sprinting (Key Toggled)]";
		}
		
		return displayText.trim();
	}

	public void updatePlayerMoveState() {
		
		player = Minecraft.getMinecraft().thePlayer;
		moveStrafe = 0.0f;
		moveForward = 0.0f;
		
		if (this.gameSettings.keyBindForward.isKeyDown())
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown())
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown())
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.isKeyDown())
        {
            --this.moveStrafe;
        }

        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        
        Optional<ToggleSprintRenderer> renderer = NakoryClient.getInstance().getHudPropertyApi().getRendererByInstance(ToggleSprintRenderer.class);
        
        if (GuiClientOptions.toggleSneak && renderer.isPresent() && renderer.get().isEnabled()) {
        	ToggleSprintRenderer mod = renderer.get();
        	if (gameSettings.keyBindSneak.isKeyDown()) {
        		if (sneakWasPressed == 0) {
        			if (sneak) sneakWasPressed = -1;
        			else if (player.isRiding() || player.capabilities.isFlying) sneakWasPressed = mod.keyHoldTicks + 1;
        			else sneakWasPressed = 1;
        			sneak = !sneak;
        		} else if (sneakWasPressed > 0) sneakWasPressed++;
        	} else {
        		if (mod.keyHoldTicks > 0 && sneakWasPressed > mod.keyHoldTicks) sneak = false;
        		sneakWasPressed = 0;
        	}
        } else sneak = gameSettings.keyBindSneak.isKeyDown();

        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
        
        if (GuiClientOptions.toggleSprint && renderer.isPresent() && renderer.get().isEnabled()) {
        	ToggleSprintRenderer mod = renderer.get();
        	
        	if (gameSettings.keyBindSprint.isKeyDown()) {
        		if (sprintWasPressed == 0) {
        			if (sprint) sprintWasPressed = -1;
        			else if(player.capabilities.isFlying) sprintWasPressed = mod.keyHoldTicks + 1;
        			else sprintWasPressed = 1;
        			sprint = !sprint;
        		} else if (sprintWasPressed > 0) sprintWasPressed++;
        	} else {
        		if (mod.keyHoldTicks > 0 && sprintWasPressed > mod.keyHoldTicks) sprint = false;
        		sprintWasPressed = 0;
        	}
        } else sprint = gameSettings.keyBindSprint.isKeyDown();
        
        if (sprint && moveForward == 1.0F && player.onGround && !player.isUsingItem() && !player.isPotionActive(Potion.blindness)) {
        	player.setSprinting(true);
        }
        
        if (GuiClientOptions.toggleFlyingBoost && renderer.isPresent() && renderer.get().isEnabled() && player.capabilities.isFlying && (Minecraft.getMinecraft().getRenderViewEntity() == player) && sprint) {
        	ToggleSprintRenderer mod = renderer.get();
        	if (boostedFlySpeed < 0.0F || this.player.capabilities.getFlySpeed() != boostedFlySpeed) originalFlySpeed = this.player.capabilities.getFlySpeed();
        	boostedFlySpeed = originalFlySpeed * mod.flyBoostFactor;
        	player.capabilities.setFlySpeed(boostedFlySpeed);
        	if (sneak) player.motionY -= 0.15D * mod.flyBoostFactor - 1.0F;
        } else {
        	if (player.capabilities.getFlySpeed() == boostedFlySpeed) this.player.capabilities.setFlySpeed(originalFlySpeed);
        	boostedFlySpeed = -1.0F;
        }
	}
}
