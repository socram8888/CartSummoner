
package ti.s4x8.bukkit.cartsummoner;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.material.Button;

import lombok.Data;

@Data
public class CartSign {
	public static final String SIGN_HEADER = "[cartsummon]";

	private int x;
	private int y;
	private int z;
	private boolean relative;

	private Block signBlock = null;

	public CartSign(Block block) throws InvalidCartSignException {
		signBlock = block;

		Material signMaterial = block.getType();
		if (!Material.SIGN_POST.equals(signMaterial) && !Material.WALL_SIGN.equals(signMaterial)) {
			throw new NotACartSignException("Not a sign!");
		}

		Sign sign = (Sign) block.getState();
		fromLines(sign.getLines());
	}

	public CartSign(String[] text) throws InvalidCartSignException {
		fromLines(text);
	}

	private void fromLines(String[] text) throws InvalidCartSignException {
		if (!SIGN_HEADER.equalsIgnoreCase(text[0])) {
			throw new NotACartSignException("Invalid header");
		}

		String[] params = Utils.cleanString(text[1] + '\n' + text[2] + '\n' + text[3]).split(" ");
		switch (params.length) {
			case 4:
				if (Utils.partialMatch("relative", params[3])) {
					relative = true;
				} else {
					throw new InvalidCartSignException("Unknown modifier \"" + params[3] + "\"");
				}
				// *** FALLTHROUGH ***

			case 3:
				try {
					x = Integer.parseInt(params[0]);
					y = Integer.parseInt(params[1]);
					z = Integer.parseInt(params[2]);
				} catch (NumberFormatException e) {
					throw new InvalidCartSignException("Invalid coords");
				}
				break;

			default:
				throw new InvalidCartSignException("Invalid amount of parameters");
		}
	}

	public static CartSign fromButton(Block buttonBlock) throws InvalidCartSignException {
		Button button;
		try {
			button = (Button) buttonBlock.getState().getData();
		} catch (ClassCastException e) {
			throw new NotACartSignException("That wasn't even a button!");
		}

		BlockFace buttonFacing = button.getFacing();
		Block signBlock = buttonBlock.getRelative(buttonFacing.getModX() * -2, 0, buttonFacing.getModZ() * -2);
		return new CartSign(signBlock);
	}

	public Location getAbsoluteLocation(Block base) {
		double locX = (relative ? base.getX() + x : x);
		double locY = (relative ? base.getY() + y : y);
		double locZ = (relative ? base.getZ() + z : z);

		return new Location(base.getWorld(), locX + 0.5, locY + 0.5, locZ + 0.5);
	}

	public Location getAbsoluteLocation() {
		if (signBlock == null) {
			throw new IllegalStateException("No known physical block attached to this sign");
		}

		return getAbsoluteLocation(signBlock);
	}
}
