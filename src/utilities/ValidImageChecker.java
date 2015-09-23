package utilities;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidImageChecker {

	public static boolean checkUsingRegex(Pattern pattern, String text) {
		boolean result = true;
		try {
			Matcher matcher = pattern.matcher(text);
			while (matcher.find())
				if (!isImage(matcher.group(1))) return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isImage(String url) {
		boolean result = false;
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod("HEAD");
			urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0");
			urlConnection.setInstanceFollowRedirects(false);
			HttpURLConnection.setFollowRedirects(false);
			String contentType = urlConnection.getHeaderField("Content-Type");
			if (contentType.startsWith("image/")) result = true;
		} catch (Exception e) {
		}
		return result;
	}

}