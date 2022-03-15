package com.github.peddach.luckpermsPrefix.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.github.peddach.luckpermsPrefix.Prefix;

public class JoinListener implements Listener {
	private Prefix plugin;

	public JoinListener(Prefix plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.plugin.getPrefixManager().setTabPrefix(event.getPlayer());
	}
}