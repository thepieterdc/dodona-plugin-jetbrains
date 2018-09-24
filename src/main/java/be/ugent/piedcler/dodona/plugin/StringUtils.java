package be.ugent.piedcler.dodona.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private static final Pattern JSON_EXTENSION = Pattern.compile(".json", Pattern.LITERAL);


	public static String removeJsonExtention(String url) {
		return JSON_EXTENSION.matcher(url).replaceAll(Matcher.quoteReplacement(""));
	}

}
