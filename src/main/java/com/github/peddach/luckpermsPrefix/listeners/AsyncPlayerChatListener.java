package com.github.peddach.luckpermsPrefix.listeners;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.destroystokyo.paper.Title.Builder;
import com.github.peddach.luckpermsPrefix.Prefix;

import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;

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
		
		Component msg = event.message();
		
		if (player.hasPermission("chat.color")) {
			String chatColorTransfolrmString = PaperComponents.plainTextSerializer().serialize(msg);
			chatColorTransfolrmString = ChatColor.translateAlternateColorCodes('&', chatColorTransfolrmString);
			msg = Component.text(chatColorTransfolrmString);
		}
		
		Component finalMessage = Component.text(format).append(msg);
		event.renderer((source, sourceDisplayName, message, viewer) -> finalMessage);
		
	}
}
