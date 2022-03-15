package com.github.peddach.luckpermsPrefix.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.github.peddach.luckpermsPrefix.Prefix;

public class QuitListener implements Listener {
	private Prefix plugin;

	public QuitListener(Prefix plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, (Plugin) plugin);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.plugin.getPrefixManager().removePlayerFromAllTeams(event.getPlayer());
	}
}