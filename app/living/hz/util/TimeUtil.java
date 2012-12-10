package living.hz.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

	private static SimpleDateFormat local = new SimpleDateFormat(
			"yyyy-mm-dd hh-mm-ss");

	public static String toLocalTime(long time) {
		return local.format(time);
	}

	@SuppressWarnings("deprecation")
	public static int getCurrentYear() {
		return new Date().getYear();
	}

	@SuppressWarnings("deprecation")
	public static long getPointTime() {
		return new Date(1970, 0, 0).getTime();
	}
}
