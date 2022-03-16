package com.github.peddach.luckpermsPrefix.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.github.peddach.luckpermsPrefix.Prefix;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

public class AsyncPlayerChatListener implements Listener {
	private Prefix plugin;

	public AsyncPlayerChatListener(Prefix plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}

	@EventHandler
	public void onPlayerChat(AsyncChatEvent event) {
		Player player = event.getPlayer();
		String format = this.plugin.getPrefixManager().getFormat().replaceAll("%prefix%", this.plugin.getPrefixManager().getChatPrefix(player));
		format = format.replaceAll("%player%", player.getName());
		format = format.replaceAll("%chatcolor%", this.plugin.getPrefixManager().getChatColor(player));
		format = ChatColor.translateAlternateColorCodes('&', format);
		Component msg = event.message().replaceText(TextReplacementConfig.builder().match("%").replacement("%%").build());
		if (player.hasPermission("chat.color"))
			msg = msg.replaceText(TextReplacementConfig.builder().match("&").replacement("§").build());
		//event.setFormat(format + msg);
		Component formatComponent = Component.text(format);
		event.message(formatComponent.append(msg));
	}
}
