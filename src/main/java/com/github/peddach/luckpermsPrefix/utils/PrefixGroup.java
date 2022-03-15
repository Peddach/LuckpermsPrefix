package com.github.peddach.luckpermsPrefix.utils;

import org.bukkit.scoreboard.Team;

public class PrefixGroup {
	private Team team;
	private String name;
	private String tabPrefix;
	private String chatPrefix;
	private String rank;
	private String chatColor;

	public PrefixGroup(String name, String tabPrefix, String chatPrefix, String rank, String chatColor, PrefixManager prefixManager) {
		this.name = name;
		this.tabPrefix = tabPrefix;
		this.chatPrefix = chatPrefix;
		this.rank = rank;
		this.chatColor = chatColor;
		this.team = prefixManager.getScoreboard().registerNewTeam(rank + name);
		this.team.setPrefix(tabPrefix.replaceAll("&", "§"));
		System.out.println("ID: " + rank);
		System.out.println("Name: " + name);
		System.out.println("Prefix: " + tabPrefix);
	}

	public Team getTeam() {
		return this.team;
	}

	public String getName() {
		return this.name;
	}

	public String getChatPrefix() {
		return this.chatPrefix;
	}

	public String getChatColor() {
		return this.chatColor;
	}
}