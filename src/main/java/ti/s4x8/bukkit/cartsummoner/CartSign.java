
package ti.s4x8.bukkit.cartsummoner;

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
	private boolean xRelative;

	private int y;
	private boolean yRelative;

	private int z;
	private boolean zRelative;

	public CartSign(Block block) throws InvalidCartSignException {
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

		String[] params = Utils.cleanString(text[1] + "\n" + text[2] + "\n" + text[3]).split(" ");
		if (params.length != 3) {
			throw new InvalidCartSignException("Invalid amount of parameters");
		}

		xRelative = (params[0].charAt(0) == '+' || params[0].charAt(0) == '-');
		yRelative = (params[1].charAt(0) == '+' || params[1].charAt(0) == '-');
		zRelative = (params[2].charAt(0) == '+' || params[2].charAt(0) == '-');

		try {
			x = Integer.parseInt(params[0]);
			y = Integer.parseInt(params[1]);
			z = Integer.parseInt(params[2]);
		} catch (NumberFormatException e) {
			throw new InvalidCartSignException("Invalid coords");
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

	public Location getLocation(Location base) {
		double locX = (xRelative ? base.getX() + x : x);
		double locY = (yRelative ? base.getY() + y : y);
		double locZ = (zRelative ? base.getZ() + z : z);

		return new Location(base.getWorld(), locX, locY, locZ);
	}
}
