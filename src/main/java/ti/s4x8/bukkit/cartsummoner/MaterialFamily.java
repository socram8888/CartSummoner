
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import com.google.common.collect.Sets;

import java.util.Set;

import lombok.experimental.Delegate;

public class MaterialFamily implements Set<Material> {

	@Delegate
	private final Set<Material> materials;

	public MaterialFamily(Set<Material> materials) {
		this.materials = Sets.immutableEnumSet(materials);
	}

	public MaterialFamily(Material ... materials) {
		this.materials = Sets.immutableEnumSet(materials[0], materials);
	}

	public boolean hasMaterial(Block block) {
		return contains(block.getType());
	}

	public boolean hasMaterial(BlockState block) {
		return contains(block.getType());
	}

	public static final MaterialFamily SAFE_AIR_BLOCKS = new MaterialFamily(
		Material.AIR,
		Material.SAPLING,
		Material.LEAVES,
		Material.POWERED_RAIL,
		Material.DETECTOR_RAIL,
		Material.WEB,
		Material.LONG_GRASS,
		Material.DEAD_BUSH,
		Material.YELLOW_FLOWER,
		Material.RED_ROSE,
		Material.BROWN_MUSHROOM,
		Material.RED_MUSHROOM,
		Material.TORCH,
		Material.REDSTONE_WIRE,
		Material.CROPS,
		Material.SIGN_POST,
		Material.WOODEN_DOOR,
		Material.LADDER,
		Material.RAILS,
		Material.WALL_SIGN,
		Material.LEVER,
		Material.STONE_PLATE,
		Material.IRON_DOOR_BLOCK,
		Material.WOOD_PLATE,
		Material.REDSTONE_TORCH_OFF,
		Material.REDSTONE_TORCH_ON,
		Material.STONE_BUTTON,
		Material.SUGAR_CANE_BLOCK,
		Material.PORTAL,
		Material.DIODE_BLOCK_OFF,
		Material.DIODE_BLOCK_ON,
		Material.PUMPKIN_STEM,
		Material.MELON_STEM,
		Material.VINE,
		Material.WATER_LILY,
		//Material.COCOA,
		Material.TRIPWIRE_HOOK,
		Material.TRIPWIRE,
		Material.FLOWER_POT,
		Material.CARROT,
		Material.POTATO,
		Material.WOOD_BUTTON,
		//Material.SKULL,
		Material.GOLD_PLATE,
		Material.IRON_PLATE,
		Material.REDSTONE_COMPARATOR_OFF,
		Material.REDSTONE_COMPARATOR_ON,
		Material.ACTIVATOR_RAIL,
		Material.LEAVES_2,
		Material.CARPET,
		Material.DOUBLE_PLANT
	);

	public static final MaterialFamily BUTTONS = new MaterialFamily(
		Material.STONE_BUTTON,
		Material.WOOD_BUTTON
	);

	public static final MaterialFamily RAILS = new MaterialFamily(
		Material.POWERED_RAIL,
		Material.DETECTOR_RAIL,
		Material.RAILS,
		Material.ACTIVATOR_RAIL
	);
}
