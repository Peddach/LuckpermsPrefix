package com.github.peddach.luckpermsPrefix.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.github.peddach.luckpermsPrefix.Prefix;

public class AsyncPlayerChatListener implements Listener {
	private Prefix plugin;

	public AsyncPlayerChatListener(Prefix plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String format = this.plugin.getPrefixManager().getFormat().replaceAll("%prefix%", this.plugin.getPrefixManager().getChatPrefix(player));
		format = format.replaceAll("%player%", player.getName());
		format = format.replaceAll("%chatcolor%", this.plugin.getPrefixManager().getChatColor(player));
		format = format.replaceAll("&", "§");
		String msg = event.getMessage().replaceAll("%", "%%");
		if (player.hasPermission("chat.color"))
			msg = msg.replaceAll("&", "§");
		event.setFormat(format + msg);
	}
}
