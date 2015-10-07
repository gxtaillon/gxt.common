package gxt.common.extension;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionExtension {
	public static String stringnify(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage());
		sb.append('\n');
		sb.append(sw.toString());
		return sb.toString();
	}
}
