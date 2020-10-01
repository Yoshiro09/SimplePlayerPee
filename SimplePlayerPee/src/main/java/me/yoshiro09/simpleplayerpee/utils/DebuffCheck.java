package me.yoshiro09.simpleplayerpee.utils;

import org.bukkit.entity.Player;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;

public class DebuffCheck {
	
	private MessagesManager messagesManager;
	private Main plugin;
	
	public DebuffCheck(Main plugin) {
		this.plugin = plugin;
		messagesManager = new MessagesManager();
	}
	
	public void check(PeeingPlayer peeingPlayer) {
		Player player = peeingPlayer.getPlayer();
		String risposta = peeingPlayer.checkSlowness();
		
		if (risposta == null) {
			return;
		}

		String prefix = plugin.getLanguageConfig().getString("prefix");
		messagesManager.sendMessage(player, prefix + "&r " + risposta);
	}

}
