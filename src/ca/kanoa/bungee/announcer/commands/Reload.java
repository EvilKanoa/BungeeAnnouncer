package ca.kanoa.bungee.announcer.commands;

import ca.kanoa.bungee.announcer.Announcer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class Reload extends Command {

	private Announcer plugin;
	
	public Reload(Announcer plugin) {
		super("announcerreload");
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("announcer.reload")) {
			sender.sendMessage(new ComponentBuilder(
					"Reloading announcer settings from file...").color(ChatColor.RED).create());
			plugin.reload();
			sender.sendMessage(new ComponentBuilder(
					"Announcer settings reloaded.").color(ChatColor.RED).create());
		}
	}

}
