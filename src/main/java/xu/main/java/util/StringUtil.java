package xu.main.java.util;

public class StringUtil {

	public static boolean isNullOrEmpty(String input) {
		return input == null || "".equals(input);
	}

	public static int nullToInt(Integer integer) {
		if (integer == null) {
			return 0;
		}
		return integer.intValue();
	}

}
