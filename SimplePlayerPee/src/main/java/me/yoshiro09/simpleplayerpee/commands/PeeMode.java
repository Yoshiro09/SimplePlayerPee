package me.yoshiro09.simpleplayerpee.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.MessagesManager;

public class PeeMode implements CommandExecutor {
	
	private Main plugin;
	private MessagesManager msgManager;
	
	public PeeMode(Main plugin, MessagesManager msgManager) {
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
		
		if (!p.hasPermission("simpleplayerpee.cmd.peemode")) {
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("errori.no-permessi").replaceAll("%permesso%", "simpleplayerpee.cmd.peemode"));
			
			return true;
		}
		
		PeeingPlayer pePlayer = plugin.peeingPlayersList.get(p.getUniqueId());
		
		if (pePlayer.getWaterLevel() < 1) {
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("peemode.livello-acqua-basso"));
			
			return true;
		}
		
		if (pePlayer.hasPeeModeActive()) {
			pePlayer.setPeeMode(false);
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("peemode.modalita-disattivata"));
			
			return true;
		}
		
		pePlayer.setPeeMode(true);
		msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("peemode.modalita-attivata"));
		
		return true;
	}

}
