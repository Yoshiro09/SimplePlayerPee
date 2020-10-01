package me.yoshiro09.simpleplayerpee.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;

@SuppressWarnings("deprecation")
public class PeeingEvent implements Listener {
	private Main plugin;

	public PeeingEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onShift(PlayerToggleSneakEvent e) {
		Player player = e.getPlayer();
		PeeingPlayer pePlayer = plugin.peeingPlayersList.get(player.getUniqueId());
		
		if (!player.isSneaking()) {
			if (pePlayer.hasPeeModeActive()) {
				pePlayer.makePeeShifted();
				return;
			}
			
			return;
		}
	}

	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent e) {
		ItemStack item = e.getItem().getItemStack();
		if (this.plugin.getNBTWrapper().hasNBTTag(item, "SimplePeeNBT").booleanValue()) {
			e.setCancelled(true);
			return;
		}
	}
}
