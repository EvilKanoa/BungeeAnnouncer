package ca.kanoa.bungee.announcer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
import net.md_5.bungee.api.plugin.Plugin;

public class Config extends net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config {

	public Config(Plugin plugin) {
		CONFIG_FILE = new File("plugins" + File.separator + plugin.getDescription().getName(), "config.yml");
		try {
			this.init();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public double MessageTimer = 3;
	public List<String> Messages = new ArrayList<String>();
	{
		Messages.add("Test message");
		Messages.add("&5Test message");
		Messages.add("&2Test message");
	}
	
}
