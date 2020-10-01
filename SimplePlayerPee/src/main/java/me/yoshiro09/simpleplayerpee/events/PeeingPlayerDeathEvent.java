package me.yoshiro09.simpleplayerpee.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;

public class PeeingPlayerDeathEvent implements Listener {
	
	private Main plugin;
	
	public PeeingPlayerDeathEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		PeeingPlayer peeingPlayer = plugin.peeingPlayersList.get(player.getUniqueId());
		
		peeingPlayer.setWaterLevel(PeeingPlayer.getMaxWaterValue() / 2);
	}
	
}
