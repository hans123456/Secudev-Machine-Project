package utilities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class TextProcessor {

	private static Whitelist whitelist;
	private static HashMap<String, String> emoticons;

	static {

		/* allow html css style but not style tag */
		whitelist = Whitelist.relaxed();
		whitelist.addAttributes("a", "href");
		whitelist.addAttributes(":all", "style");

		/* for emoticons */
		emoticons = new HashMap<String, String>();
		emoticons.put(":)", "<img src='images/board/smile.png'>");
		emoticons.put(":)", "<img src='images/board/smile.png'>");

	}

	public static String process(String text) {
		String result = text;
		result = Jsoup.clean(text, whitelist);
		result = processEmoticons(result);
		return result;
	}

	private static String processEmoticons(String text) {
		String result = text;
		for (Entry<String, String> emoticon : emoticons.entrySet())
			result = result.replaceAll(Pattern.quote(emoticon.getKey()), emoticon.getValue());
		return result;
	}

	public static void main(String[] args) {
		System.out.println(TextProcessor.process(
				"<style> </style>:) <img src='http://test'/><a href=\"http://test\">test</a><style>.b{font-weight:bold;}</style><div class='b'>test</div>"));
	}

}
