
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SignCreationListener implements Listener {
	private CSPlugin plugin;
	
	public SignCreationListener(CSPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		InvalidCartSignException invalid = null;
		try {
			new CartSign(event.getLines());
		} catch (NotACartSignException e) {
			return;
		} catch (InvalidCartSignException e) {
			invalid = e;
		}

		Player player = event.getPlayer();
		if (!player.hasPermission(plugin.getCreatePermission())) {
			player.sendMessage(ChatColor.RED + "You're not allowed to create cart summoners");
			event.setCancelled(true);
			return;
		}

		if (invalid != null) {
			player.sendMessage(ChatColor.RED + "Error creating summoner: " + invalid.getMessage());
			event.setCancelled(true);
			return;
		}

		player.sendMessage(ChatColor.GREEN + "Summoner created!");
	}
}
