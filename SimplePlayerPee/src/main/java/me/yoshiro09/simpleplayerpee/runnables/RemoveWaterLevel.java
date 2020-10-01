package me.yoshiro09.simpleplayerpee.runnables;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.RandomNumber;

public class RemoveWaterLevel {
	
	private Main plugin;
	private RandomNumber randomNumber;
	
	public RemoveWaterLevel(Main plugin, RandomNumber randomNumber) {
		this.plugin = plugin;
		this.randomNumber = randomNumber;
	}
	
	public void start() {
		(new BukkitRunnable() {
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("simpleplayerpee.bypass") || p.getGameMode().equals(GameMode.CREATIVE)) {
						continue;
					}
					
					PeeingPlayer peeingPlayer = plugin.peeingPlayersList.get(p.getUniqueId());
					peeingPlayer.removeWaterLevel(randomNumber.getRandomNumber(1, 2));
				}
			}
		}).runTaskTimer((Plugin) plugin, 0L, 20 * 60 * 4);
	}
	
}
