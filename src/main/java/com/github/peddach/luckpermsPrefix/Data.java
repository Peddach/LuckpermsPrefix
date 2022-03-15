
package com.github.peddach.luckpermsPrefix;

public class Data {
	private Prefix plugin;
	private String prefix = "§7[§ePrefix§7] ";
	private String noPerm = this.prefix + "§cDazu hast du keine Rechte.";

	public Data(Prefix plugin) {
		this.plugin = plugin;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getNoPerm() {
		return this.noPerm;
	}
}