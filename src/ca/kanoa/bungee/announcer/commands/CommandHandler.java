package ca.kanoa.bungee.announcer.commands;

import java.util.ArrayList;
import java.util.List;

import ca.kanoa.bungee.announcer.commands.Subcommand.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandHandler extends Command {

	private List<Subcommand> commands;

	public CommandHandler(String name, Subcommand...subcommands) {
		super(name);
		commands = new ArrayList<Subcommand>();
		for (Subcommand c : subcommands) {
			commands.add(c);
		}
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String[] subargs;
			if (args.length > 1) {
				subargs = new String[args.length-1];
				for (int i = 1; i < args.length; i++) {
					subargs[i-1] = args[i];
				}
			} else {
				subargs = new String[0];
			}

			for (Subcommand c : commands) {
				if (c.getCommand().equalsIgnoreCase(args[0])) {
					if (sender.hasPermission(c.getPermission()) || c.getPermission().equalsIgnoreCase("null")) {
						if (c.getWho() == User.ALL || (c.getWho() == User.CONSOLE && 
								!(sender instanceof ProxiedPlayer)) || 
								(c.getWho() == User.PLAYER && sender instanceof ProxiedPlayer)) {
							c.execute(sender, subargs);
							return;
						} else {
							sender.sendMessage(new ComponentBuilder("You can't use this command!").create());
							return;
						}
					} else {
						sender.sendMessage(new ComponentBuilder("You don't have permission!").create());
						return;
					}
				}
			}
			sender.sendMessage(new ComponentBuilder("Unknown commmand.").create());
		} else {
			for (Subcommand c : commands) {
				if (c.isNoargs()) {
					if (sender.hasPermission(c.getPermission()) || c.getPermission().equalsIgnoreCase("null")) {
						if (c.getWho() == User.ALL || (c.getWho() == User.CONSOLE && 
								!(sender instanceof ProxiedPlayer)) || 
								(c.getWho() == User.PLAYER && sender instanceof ProxiedPlayer)) {
							c.execute(sender, args);
							return;
						} else {
							sender.sendMessage(new ComponentBuilder("You can't use this command!").create());
							return;
						}
					} else {
						sender.sendMessage(new ComponentBuilder("You don't have permission!").create());
						return;
					}
				}
			}
			sender.sendMessage(new ComponentBuilder("Unknown commmand.").create());
		}
	}

}
