
package es.dracon.bukkit.cartsummoner;

import org.bukkit.plugin.java.JavaPlugin;

public class CSPlugin extends JavaPlugin {
	public static final String CARTSUMMON_SPAWNED_META = "cartsummon.spawned";

	public void onEnable() {
		new CartSummonListener(this);
		new CartLeaveListener(this);
	}
}
