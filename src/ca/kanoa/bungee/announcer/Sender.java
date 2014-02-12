package ca.kanoa.bungee.announcer;

public class Sender implements Runnable {

	private Announcer plugin;
	private int messageNumber;
	
	public Sender(Announcer plugin) {
		this.plugin = plugin;
		messageNumber = 0;
	}
	
	@Override
	public void run() {
		if (plugin.getConfig().Messages.size() == 0) {
			return;
		}
		String message = plugin.getConfig().Messages.get(messageNumber);
		plugin.send(message);
		messageNumber++;
		if (messageNumber >= plugin.getConfig().Messages.size()) {
			messageNumber = 0;
		}
	}
	
	public void reset() {
		messageNumber = 0;
	}

}
