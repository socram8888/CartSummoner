
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class CartSummonListener implements Listener {
	private final Vector BLOCK_CENTER = new Vector(0.5, 0.5, 0.5);

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

		Block buttonBlock = event.getClickedBlock();
		Location cartLocation = calculateCartLocation(buttonBlock);
		if (cartLocation == null) {
			return;
		}

		RideableMinecart cart = (RideableMinecart) cartLocation.getWorld().spawnEntity(cartLocation, EntityType.MINECART);
		if (cart == null) {
			player.sendMessage(ChatColor.RED + "Plugin has been unable to spawn a cart");
			return;
		}

		// Mark as spawned by this plugin
		cart.setMetadata(CSPlugin.CARTSUMMON_SPAWNED_META, new FixedMetadataValue(plugin, true));
		cart.setPassenger(player);

		player.sendMessage(ChatColor.GREEN + "Here's your cart. Have fun!");
	}

	/*
	This function checks if the blocks the button is facing to matches this pattern
	┌───────┬───────┐
	│  ???  │ SAFE  │
	│       │  AIR  │
	├───────┼───────┤
	│ BUT-  │ SAFE  │
	│ TON   │  AIR  │
	├───────┼───────┤
	│ REDS- │ POWR. │
	│ TONE  │ RAIL  │
	└───────┴───────┘
	Check MaterialFamily.SAFE_AIR_BLOCKS for blocks I've considered as "safe". They're all either non-solid blocks, or blocks with a very small bounding box.
	*/
	private Location calculateCartLocation(Block buttonBlock) {
		if (!MaterialFamily.BUTTONS.hasMaterial(buttonBlock)) {
			return null;
		}

		if (buttonBlock.getRelative(BlockFace.DOWN).getType() != Material.REDSTONE_WIRE) {
			return null;
		}

		Button button = (Button) buttonBlock.getState().getData();
		BlockFace buttonFacing = button.getFacing();

		Block airBlock1 = buttonBlock.getRelative(buttonFacing);
		if (!MaterialFamily.SAFE_AIR_BLOCKS.hasMaterial(airBlock1)) {
			return null;
		}

		Block airBlock2 = airBlock1.getRelative(BlockFace.UP);
		if (!MaterialFamily.SAFE_AIR_BLOCKS.hasMaterial(airBlock2)) {
			return null;
		}

		Block railBlock = airBlock1.getRelative(BlockFace.DOWN);
		if (railBlock.getType() != Material.POWERED_RAIL) {
			return null;
		}

		return railBlock.getLocation().add(BLOCK_CENTER);
	}
}
