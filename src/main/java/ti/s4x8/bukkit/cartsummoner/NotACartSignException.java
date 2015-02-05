
package ti.s4x8.bukkit.cartsummoner;

public class NotACartSignException extends InvalidCartSignException {
	public NotACartSignException() {
		super("That wasn't a CartSummoner sign");
	}

	public NotACartSignException(String desc) {
		super(desc);
	}
}
