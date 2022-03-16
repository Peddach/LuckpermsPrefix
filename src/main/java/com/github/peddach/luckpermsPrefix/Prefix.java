package com.github.peddach.luckpermsPrefix;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.peddach.luckpermsPrefix.commands.PrefixCommand;
import com.github.peddach.luckpermsPrefix.listeners.AsyncPlayerChatListener;
import com.github.peddach.luckpermsPrefix.listeners.CommandListener;
import com.github.peddach.luckpermsPrefix.listeners.JoinListener;
import com.github.peddach.luckpermsPrefix.listeners.QuitListener;
import com.github.peddach.luckpermsPrefix.utils.PrefixManager;

public class Prefix extends JavaPlugin {
	private PrefixManager prefixManager;
	private Data data;
	private static Prefix plugin;

	public void onEnable() {
		plugin = this;
		plugin.saveDefaultConfig();
		plugin.saveConfig();
		plugin.reloadConfig();
		this.data = new Data(this);
		this.prefixManager = new PrefixManager(this);
		initListeners();
		initCommands();
	}

	private void initCommands() {
		new PrefixCommand(this);
	}

	private void initListeners() {
		new JoinListener(this);
		new AsyncPlayerChatListener(this);
		new CommandListener(this);
		new QuitListener(this);
	}

	public PrefixManager getPrefixManager() {
		return this.prefixManager;
	}

	public Data getData() {
		return this.data;
	}

	public static Prefix getPlugin() {
		return plugin;
	}
}