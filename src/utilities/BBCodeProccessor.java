package utilities;

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
		System.out.println(BBCodeProccessor.process("[noparse][noparse][b]t	t[/b][/noparse][/noparse]"));
	}

}
