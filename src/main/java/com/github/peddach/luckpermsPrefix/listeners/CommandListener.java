package com.github.peddach.luckpermsPrefix.listeners;

import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.github.peddach.luckpermsPrefix.Prefix;

public class CommandListener implements Listener {
	private Prefix plugin;

	public CommandListener(Prefix plugin) {
		this.plugin = plugin;
		plugin.getPrefixManager().getLuckPermsApi().getEventBus().subscribe(UserDataRecalculateEvent.class, this::onGroupUpdate);
	}

	public void onGroupUpdate(UserDataRecalculateEvent event) {
		Bukkit.getScheduler().runTask((Plugin) this.plugin, () -> {
			Player player = Bukkit.getPlayer(event.getUser().getUniqueId());
			if (player != null)
				this.plugin.getPrefixManager().setTabPrefix(player);
		});
	}
}