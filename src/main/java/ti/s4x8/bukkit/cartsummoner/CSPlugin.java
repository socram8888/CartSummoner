
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class CSPlugin extends JavaPlugin {
	public static final String CARTSUMMON_SPAWNED_META = "cartsummon.spawned";

	@Getter private Permission createPermission = new Permission("cartsummoner.create", "Allows creation of cart summoners", PermissionDefault.OP);
	@Getter private Permission summonPermission = new Permission("cartsummoner.summon", "Allows to summon a cart", PermissionDefault.TRUE);
	@Getter private Permission seeFailPermission = new Permission("cartsummoner.see-fail", "Player can see fail messages", PermissionDefault.TRUE);

	public void onEnable() {
		new SignCreationListener(this);
		new CartSummonListener(this);
		new CartLeaveListener(this);
	}
}
