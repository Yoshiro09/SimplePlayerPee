package me.yoshiro09.simpleplayerpee.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.DebuffCheck;

public class SessionsEvent implements Listener {

	private Main plugin;
	private DebuffCheck debuffCheck;

	public SessionsEvent(Main plugin, DebuffCheck debuffCheck) {
		this.plugin = plugin;
		this.debuffCheck = debuffCheck;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerJoinEvent(PlayerJoinEvent e) {
		PeeingPlayer pp = new PeeingPlayer(e.getPlayer());

		if (pp.hasData()) {
			pp.loadData();
			
			if (!pp.getPlayer().hasPermission("simpleplayerpee.bypass")) {
				debuffCheck.check(pp);
			}
		} else {
			pp.createData();
		}

		plugin.peeingPlayersList.put(e.getPlayer().getUniqueId(), pp);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerLeaveEvent(PlayerQuitEvent e) {
		PeeingPlayer pp = plugin.peeingPlayersList.get(e.getPlayer().getUniqueId());
		pp.saveData();
		plugin.peeingPlayersList.remove(e.getPlayer().getUniqueId());
	}
}
