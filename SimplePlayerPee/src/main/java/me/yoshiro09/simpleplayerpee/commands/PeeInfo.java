package me.yoshiro09.simpleplayerpee.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yoshiro09.simpleplayerpee.Main;
import me.yoshiro09.simpleplayerpee.api.PeeingPlayer;
import me.yoshiro09.simpleplayerpee.utils.MessagesManager;

public class PeeInfo implements CommandExecutor {

	private Main plugin;
	private MessagesManager msgManager;

	public PeeInfo(Main plugin, MessagesManager msgManager) {
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

		if (!p.hasPermission("simpleplayerpee.cmd.peeinfo")) {
			msgManager.sendMessage(p, prefix + "&r " + plugin.getLanguageConfig().getString("errori.no-permessi")
					.replaceAll("%permesso%", "simpleplayerpee.cmd.peeinfo"));

			return true;
		}
		
		PeeingPlayer pp = plugin.peeingPlayersList.get(p.getUniqueId());

		msgManager.sendMessage(p, "&8&m----------------{ &r&e&lPeeInfo&8&m }----------------");
		msgManager.sendMessage(p, "&8&l» &7Quantità d'acqua nel tuo corpo&8: &e" + pp.getWaterLevel() + "&7/"
				+ PeeingPlayer.getMaxWaterValue());
		msgManager.sendMessage(p, "&8&l» &7Puoi usare la PeeMode&8: &e" + (pp.getWaterLevel() >= 1 ? "&aSì" : "&cNo"));
		msgManager.sendMessage(p, "&8&l» &7Puoi usare la SuperPee&8: &e" + (pp.getWaterLevel() >= 6 ? "&aSì" : "&cNo"));
		msgManager.sendMessage(p, "&8&l» &7Stato PeeMode&8: &e" + (pp.hasPeeModeActive() ? "&aAttivata" : "&cDisattivata"));
		msgManager.sendMessage(p, "&8&l» &7Stato SuperPee&8: &e" + (pp.hasSuperPeeActive() ? "&aAttivata" : "&cDisattivata"));
		msgManager.sendMessage(p, "&8&m----------------{---------}----------------");
		return true;
	}

}
