
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class CartSummonListener implements Listener {
	private CSPlugin plugin;

	public CartSummonListener(CSPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		Player player = event.getPlayer();
		// Don't let the player spawn a Minecart if he's already riding something
		if (player.isInsideVehicle()) {
			return;
		}

		CartSign sign;
		Block buttonBlock = event.getClickedBlock();
		try {
			sign = CartSign.fromButton(buttonBlock);
		} catch (InvalidCartSignException e) {
			return;
		}

		if (!player.hasPermission(plugin.getSummonPermission())) {
			if (player.hasPermission(plugin.getSeeFailPermission())) {
				player.sendMessage(ChatColor.RED + "You don't have permission to summon a cart");
			}

			return;
		}

		World world = buttonBlock.getWorld();
		RideableMinecart cart = (RideableMinecart) world.spawnEntity(new Location(world, sign.getX() + 0.5, sign.getY() + 0.5, sign.getZ() + 0.5), EntityType.MINECART);
		if (cart == null) {
			player.sendMessage(ChatColor.RED + "Plugin was unable to spawn a cart");
			return;
		}

		// Mark as spawned by this plugin
		cart.setMetadata(CSPlugin.CARTSUMMON_SPAWNED_META, new FixedMetadataValue(plugin, true));
		cart.setPassenger(player);

		player.sendMessage(ChatColor.GREEN + "Here's your cart. Have fun!");
	}
}
