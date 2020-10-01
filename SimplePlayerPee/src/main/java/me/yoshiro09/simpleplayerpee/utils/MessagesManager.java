package me.yoshiro09.simpleplayerpee.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessagesManager {
	
	public static String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public void sendMessage(Player p, String msg) {
		p.sendMessage(translate(msg));
	}
}
