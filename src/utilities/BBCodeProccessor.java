package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

public class BBCodeProccessor {

	static TextProcessor processor;

	static {
		processor = BBProcessorFactory.getInstance()
				.create(BBCodeProccessor.class.getResourceAsStream("BBCodeProcessor.xml"));
	}

	public static String process(String input) {
		return processor.process(input);
	}

	public static void main(String[] args) {
		System.out.println(BBCodeProccessor.process("<style>.b{font-weight:bold;}</style><div class='b'>test</div>"));
	}

}
