package com.github.peddach.luckpermsPrefix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.peddach.luckpermsPrefix.Prefix;

public class PrefixCommand implements CommandExecutor {
	private Prefix plugin;

	public PrefixCommand(Prefix plugin) {
		this.plugin = plugin;
		plugin.getCommand("prefix").setExecutor(this);
	}

	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
		if (commandSender.hasPermission("prefix.admin")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("update")) {
					this.plugin.getPrefixManager().setTabPrefixAll();
					commandSender.sendMessage(this.plugin.getData().getPrefix() + "Du hast die Prefixe für alle Spieler neu gesetzt.");
				} else if (args[0].equalsIgnoreCase("reload")) {
					this.plugin.getPrefixManager().loadGroups();
					commandSender.sendMessage(this.plugin.getData().getPrefix() + "Du hast die Prefixe neu geladen.");
				} else {
					commandSender.sendMessage(this.plugin.getData().getPrefix() + "Bitte nutze: /prefix <reload/update>");
				}
			} else {
				commandSender.sendMessage(this.plugin.getData().getPrefix() + "Bitte nutze: /prefix <reload/update>");
			}
		} else {
			commandSender.sendMessage(this.plugin.getData().getNoPerm());
		}

		return false;
	}
}
