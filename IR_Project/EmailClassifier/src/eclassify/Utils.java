package eclassify;

import org.jsoup.Jsoup;

public class Utils {

	public static String strip(String str)
	{
		return Jsoup.parse(str).text();
	}
}
