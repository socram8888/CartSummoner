
package ti.s4x8.bukkit.cartsummoner;

public class InvalidCartSignException extends Exception {
	public InvalidCartSignException() {
		this("Invalid CartSummoner sign");
	}

	public InvalidCartSignException(String desc) {
		super(desc);
	}
}
