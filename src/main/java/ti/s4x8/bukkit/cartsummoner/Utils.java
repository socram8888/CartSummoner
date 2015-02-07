
package ti.s4x8.bukkit.cartsummoner;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class Utils {
	private static final Pattern MULTISPACE = Pattern.compile("[ \n\t]+");
	private static final Pattern SPECIAL = Pattern.compile("§[0-9a-fA-Fk-rK-R]");
	public static final int TICKS_PER_SECOND = 20;

	private Utils() { }

	public static final String cleanString(String text) {
		text = text.trim();
		text = MULTISPACE.matcher(text).replaceAll(" ");
		text = SPECIAL.matcher(text).replaceAll("");
		return text;
	}

	public static int secondsToTicks(int seconds) {
		return seconds * TICKS_PER_SECOND;
	}

	public static boolean partialMatch(String a, String b) {
		if (a == null) {
			return b == null;
		}

		int len = Math.min(a.length(), b.length());
		if (len == 0) {
			return false;
		}

		return a.substring(0, len).equalsIgnoreCase(b.substring(0, len));
	}
}
