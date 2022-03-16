
package com.github.peddach.luckpermsPrefix;

import org.bukkit.ChatColor;

public class Data {
	private String prefix = "§7[§ePrefix§7] ";
	private String noPerm = this.prefix + "Dazu hast du keine Rechte.";

	public Data(Prefix plugin) {
		prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix"));
		noPerm = prefix + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPerm"));
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getNoPerm() {
		return this.noPerm;
	}
}