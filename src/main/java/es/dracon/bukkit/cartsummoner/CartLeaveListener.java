
package es.dracon.bukkit.cartsummoner;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class CartLeaveListener implements Listener {
	private CSPlugin plugin;

	public CartLeaveListener(CSPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onVehicleExit(VehicleExitEvent event) {
		Vehicle vehicle = event.getVehicle();
		if (vehicle.hasMetadata(CSPlugin.CARTSUMMON_SPAWNED_META)) {
			vehicle.remove();
		}
	}
}
