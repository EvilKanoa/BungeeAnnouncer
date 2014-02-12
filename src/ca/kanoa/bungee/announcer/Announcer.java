package ca.kanoa.bungee.announcer;

import java.util.concurrent.TimeUnit;

import ca.kanoa.bungee.announcer.commands.CommandHandler;
import ca.kanoa.bungee.announcer.commands.Subcommand;
import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/**
 * An auto announcer built to run on the BungeeCord server for multiple servers.
 * @author Kanoa
 *
 */
public class Announcer extends Plugin {

	private CommandHandler commandHandler;
	private Config config;
	private Sender sender;
	private ScheduledTask task;
	
	@Override
	public void onEnable() {
		commandHandler = new CommandHandler("announcer", new Subcommand("reload", "annoucer.reload", this) {
			
			@Override
			public void execute(CommandSender sender, String[] args) {
				sender.sendMessage(new ComponentBuilder(
						"Reloading announcer settings from file...").color(ChatColor.RED).create());
				((Announcer) (plugin)).reload();
				sender.sendMessage(new ComponentBuilder(
						"Announcer settings reloaded.").color(ChatColor.RED).create());
			}
		}, new Subcommand("help", this) {

			{
				this.noargs(true);
			}
			
			@Override
			public void execute(CommandSender sender, String[] args) {
				sender.sendMessage(new ComponentBuilder(
						plugin.getDescription().getName())
						.color(ChatColor.RED)
						.append(" v" + plugin.getDescription().getVersion())
						.color(ChatColor.YELLOW).append(" by ").color(ChatColor.WHITE).
						append(plugin.getDescription().getAuthor()).color(ChatColor.GOLD).create());
				sender.sendMessage(new ComponentBuilder(
						"Auto annouces whatever is in the config.")
						.create());
				sender.sendMessage(new ComponentBuilder(
						"Use /annoucer reload to reload info from config")
						.create());
			}
			
		});
		getProxy().getPluginManager().registerCommand(this, commandHandler);
		config = new Config(this);
		sender = new Sender(this);
		task = getProxy().getScheduler().schedule(this, sender, 
				Math.round(config.MessageTimer * 1000), 
				Math.round(config.MessageTimer * 1000), 
				TimeUnit.MILLISECONDS);
		getProxy().getLogger().info(getDescription().getName() + " v" + 
				getDescription().getVersion() + " enabled.");
	}

	/**
	 * Reloads the configuration (time and messages) from file
	 */
	public void reload() {
		task.cancel();
		try {
			config.reload();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		sender.reset();
		task = getProxy().getScheduler().schedule(this, sender, 
				Math.round(config.MessageTimer * 1000), 
				Math.round(config.MessageTimer * 1000), 
				TimeUnit.MILLISECONDS);
	}
	
	public Config getConfig() {
		return config;
	}
	
	/**
	 * Announces a message
	 * @param rawMessage The raw text of a message with colors/text effects.
	 */
	public void send(String rawMessage) {
		for (String str : rawMessage.split("&NEW_LINE;")) {
			getProxy().broadcast(format(str));
		}
	}
	
	private BaseComponent[] format(String str) {
		ComponentBuilder builder = new ComponentBuilder("");
		char[] chars = str.toCharArray();
		
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '&' && i+1 < chars.length) {
				ChatColor c = ChatColor.getByChar(chars[i+1]);
				if (c != null) {
					switch (c) {
					case MAGIC:
						builder.obfuscated(true);
						break;
					case BOLD:
						builder.bold(true);
						break;
					case STRIKETHROUGH:
						builder.strikethrough(true);
						break;
					case UNDERLINE:
						builder.underlined(true);
						break;
					case ITALIC:
						builder.italic(true);
						break;
					case RESET:
						builder.obfuscated(false);
						builder.bold(false);
						builder.strikethrough(false);
						builder.underlined(false);
						builder.italic(false);
						builder.color(ChatColor.WHITE);
						break;
					default:
						builder.color(c);
						break;
					}
					i++;
					continue;
				}
			}
			builder.append(Character.toString(chars[i]));
		}
		return builder.create();
	}
	
}
