package me.yoshiro09.simpleplayerpee.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.yoshiro09.simpleplayerpee.api.customevents.PlayerWaterLevelChangedEvent;
import me.yoshiro09.simpleplayerpee.utils.DebuffCheck;

public class WaterLevelChangedEvent implements Listener {
	
	private DebuffCheck debuffCheck;
	
	public WaterLevelChangedEvent(DebuffCheck debuffCheck) {
		this.debuffCheck = debuffCheck;
	}
	
	@EventHandler
	public void onWaterLevelChange(PlayerWaterLevelChangedEvent e) {
		debuffCheck.check(e.getPeeingPlayer());
	}
	
}
