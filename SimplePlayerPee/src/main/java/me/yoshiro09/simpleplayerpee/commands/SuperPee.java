package me.yoshiro09.simpleplayerpee.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.MessagesManager;

public class SuperPee implements CommandExecutor {
	
	private Main plugin;
	private MessagesManager msgManager;
	
	public SuperPee(Main plugin, MessagesManager msgManager) {
		this.plugin = plugin;
		this.msgManager = msgManager;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			Bukkit.getLogger().info("Only players can execute this command!");
			return true;
		}
		
		String prefix = plugin.getLanguageConfig().getString("prefix");
		
		Player p = (Player) sender;
		
		if (!p.hasPermission("simpleplayerpee.cmd.superpee")) {
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("errori.no-permessi").replaceAll("%permesso%", "simpleplayerpee.cmd.superpee"));
			
			return true;
		}
		
		PeeingPlayer pePlayer = plugin.peeingPlayersList.get(p.getUniqueId());
		
		if (pePlayer.getWaterLevel() < 6) {
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("superpee.livello-acqua-basso"));
			
			return true;
		}
		
		if (pePlayer.hasSuperPeeActive()) {
			pePlayer.setSuperPee(false);
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("superpee.superpee-disattivata"));
			
			return true;
		}
		
		pePlayer.setSuperPee(true);
		msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("superpee.superpee-attivata"));
		
		return true;
	}

}
