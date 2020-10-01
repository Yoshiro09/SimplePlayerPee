package me.yoshiro09.simpleplayerpee.api.customevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;

public class PlayerWaterLevelChangedEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	
	private PeeingPlayer peeingPlayer;
	
	public PlayerWaterLevelChangedEvent(PeeingPlayer peeingPlayer) {
		this.peeingPlayer = peeingPlayer;
	}
	
	public PeeingPlayer getPeeingPlayer() {
		return peeingPlayer;
	}
	
	public double getWaterLevel() {
		return peeingPlayer.getWaterLevel();
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
