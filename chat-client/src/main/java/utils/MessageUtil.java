package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 10:02
 */
public class MessageUtil {
	public static void sendJSON(String json, OutputStream os) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(os);
		writer.write(json);
		writer.flush();
	}
}
