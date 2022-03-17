package com.github.peddach.luckpermsPrefix.utils;

import java.util.ArrayList;

import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.github.peddach.luckpermsPrefix.Prefix;

public class PrefixManager {
	private LuckPerms api;
	private Prefix plugin;
	private ArrayList<PrefixGroup> prefixGroups;
	private Scoreboard scoreboard;
	private String format;
	private FileConfiguration cfg = Prefix.getPlugin().getConfig();

	public PrefixManager(Prefix plugin) {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
			this.api = (LuckPerms) provider.getProvider();
		} else {
			Bukkit.getConsoleSender().sendMessage("LuckPerms konnte nicht gefunden werden.");
			return;
		}
		this.plugin = plugin;
		loadGroups();
	}

	public void loadGroups() {
		this.cfg.set("chatFormat", "%prefix%%player%&7: %chatcolor%");
		for (Group group : this.api.getGroupManager().getLoadedGroups()) {
			this.cfg.set("Groups." + group.getName() + ".TabPrefix", group.getCachedData().getMetaData().getPrefix());
			this.cfg.set("Groups." + group.getName() + ".ChatPrefix", group.getCachedData().getMetaData().getPrefix());
			String rank = "00";
			if (group.getWeight().isPresent())
				rank = (group.getWeight().getAsInt() > 9) ? String.valueOf(group.getWeight().getAsInt()) : ("0" + group.getWeight().getAsInt());
			this.cfg.set("Groups." + group.getName() + ".Rank", rank);
			this.cfg.set("Groups." + group.getName() + ".ChatColor", "&7");
		}
		Prefix.getPlugin().saveConfig();
		Prefix.getPlugin().reloadConfig();

		this.format = this.cfg.getString("chatFormat");
		this.prefixGroups = new ArrayList<>();
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		for (String group : this.cfg.getConfigurationSection("Groups").getKeys(false))
			this.prefixGroups.add(new PrefixGroup(group, this.cfg.getString("Groups." + group + ".TabPrefix"), this.cfg.getString("Groups." + group + ".ChatPrefix"), this.cfg.getString("Groups." + group + ".Rank"), this.cfg.getString("Groups." + group + ".ChatColor"), this));
		setTabPrefixAll();
	}

	public String getChatPrefix(Player player) {
		return getPrefixGroup(player).getChatPrefix();
	}

	public String getChatColor(Player player) {
		return getPrefixGroup(player).getChatColor();
	}

	public void setTabPrefixAll() {
		(new BukkitRunnable() {
			public void run() {
				for (Player a : Bukkit.getOnlinePlayers()) {
					PrefixManager.this.setTabPrefix(a);
				}
			}
		}).runTaskLater((Plugin) this.plugin, 2L);
	}

	public void setTabPrefix(final Player player) {
		(new BukkitRunnable() {
			public void run() {
				PrefixManager.this.removePlayerFromAllTeams(player);
				Team team = PrefixManager.this.getPrefixGroup(player).getTeam();
				team.addPlayer((OfflinePlayer) player);
				String displayName = team.getPrefix() + player.getName();
				for (Player a : Bukkit.getOnlinePlayers()) {
					a.setScoreboard(PrefixManager.this.scoreboard);
				}
				player.playerListName(Component.text(ChatColor.translateAlternateColorCodes('&', displayName)));
			}
		}).runTaskLater((Plugin) this.plugin, 2L);
	}

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public void removePlayerFromAllTeams(Player player) {
		for (Team team : this.scoreboard.getTeams()) {
			if(team.getEntries().contains(player.getName())) {
				team.removePlayer((OfflinePlayer) player);
			}
		}
	}

	public PrefixGroup getPrefixGroup(Player player) {
		for (PrefixGroup group : this.prefixGroups) {
			if (this.api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup().equals(group.getName()))
				return group;
		}
		return null;
	}

	public ArrayList<PrefixGroup> getPrefixGroups() {
		return this.prefixGroups;
	}

	public String getFormat() {
		return this.format;
	}

	public LuckPerms getLuckPermsApi() {
		return this.api;
	}
}