package me.yoshiro09.simpleplayerpee.runnables;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;

public class DamagePlayer {
	
	private Main plugin;
	
	public DamagePlayer(Main plugin) {
		this.plugin = plugin;
	}
	
	public void start() {
		(new BukkitRunnable() {
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("simpleplayerpee.bypass") || p.getGameMode().equals(GameMode.CREATIVE)) {
						continue;
					}
					
					PeeingPlayer peeingPlayer = plugin.peeingPlayersList.get(p.getUniqueId());
					
					if (peeingPlayer.getWaterLevel() > 8 || peeingPlayer.getWaterLevel() < 2) {
						peeingPlayer.getPlayer().damage(1.5);
					}
				}
			}
		}).runTaskTimer((Plugin) plugin, 0L, 20 * 2);
	}
}
