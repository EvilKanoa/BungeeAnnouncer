package ca.kanoa.bungee.announcer.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class Subcommand {

	private String command;
	private String permission;
	private User who;
	private boolean noargs;
	protected Plugin plugin;
	
	public Subcommand(String command, String permission, User who, Plugin plugin) {
		this.setCommand(command);
		this.setPermission(permission);
		this.setWho(who);
		this.plugin = plugin;
	}
	
	public Subcommand(String command, User who, Plugin plugin) {
		this.setCommand(command);
		this.setPermission("null");
		this.setWho(who);
		this.plugin = plugin;
	}
	
	public Subcommand(String command, String permission, Plugin plugin) {
		this.setCommand(command);
		this.setPermission(permission);
		this.setWho(User.ALL);
		this.plugin = plugin;
	}
	
	public Subcommand(String command, Plugin plugin) {
		this.setCommand(command);
		this.setPermission("null");
		this.setWho(User.ALL);
		this.plugin = plugin;
	}
	
	/**
	 * Executes this subcommand with the given arguments
	 * @param sender The sender of the command (either player or console)
	 * @param args The args for the command
	 */
	public abstract void execute(CommandSender sender, String[] args);
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public User getWho() {
		return who;
	}

	public void setWho(User who) {
		this.who = who;
	}

	/**
	 * Sets weather this will be called when no subcommand is provided
	 * @param noargs If it's called
	 */
	public void noargs(boolean noargs) {
		this.noargs = noargs;
	}
	
	public boolean isNoargs() {
		return this.noargs;
	}

	public enum User {
		CONSOLE, PLAYER, ALL;
	}
	
}
