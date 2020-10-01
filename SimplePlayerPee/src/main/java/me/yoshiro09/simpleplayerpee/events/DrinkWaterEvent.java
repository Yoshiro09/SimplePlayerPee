package me.yoshiro09.simpleplayerpee.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.DebuffCheck;
import me.yoshiro09.simpleplayerpee.utils.RandomNumber;

public class DrinkWaterEvent implements Listener {

	private Main plugin;
	private DebuffCheck debuffCheck;
	private RandomNumber randomNumber;

	public DrinkWaterEvent(Main plugin, DebuffCheck debuffCheck, RandomNumber randomNumber) {
		this.plugin = plugin;
		this.debuffCheck = debuffCheck;
		this.randomNumber = randomNumber;
	}

	@EventHandler
	public void onPlayerDrinkWater(PlayerItemConsumeEvent e) {
		PeeingPlayer pp = plugin.peeingPlayersList.get(e.getPlayer().getUniqueId());

		if (e.getItem().getType().equals(Material.POTION) || e.getItem().getType().equals(Material.BEETROOT_SOUP)
				|| e.getItem().getType().equals(Material.MUSHROOM_STEW)
				|| e.getItem().getType().equals(Material.MELON_SLICE)) {

			if (!pp.getPlayer().hasPermission("simpleplayerpee.bypass")) {
				if (pp.getWaterLevel() == 10) {
					e.setCancelled(true);
					debuffCheck.check(pp);
				}
			}

			pp.addWaterLevel(randomNumber.getRandomNumber(0.5, 2));
		} else if (e.getItem().getType().equals(Material.MILK_BUCKET)) {
			if (!pp.getPlayer().hasPermission("simpleplayerpee.bypass")) {
				debuffCheck.check(pp);
			}
		}
	}

}
