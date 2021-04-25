package utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 22:58
 */
public class MessageUtil {

	private static Logger logger = Logger.getLogger(MessageUtil.class);

	public static void sendWithOutputStream(String json, OutputStream os) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(os);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			logger.debug("[MessageUtil]: send failed: " + e);
		}
	}

}
