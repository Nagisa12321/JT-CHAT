package control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import org.apache.log4j.Logger;
import util.JSONParser;
import util.JSONType;

import java.io.InputStream;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 18:40
 */
public class ChatServerSocket implements Runnable {

	private IController controller;
	private User user;
	private Logger logger;
	private boolean isRunning;


	public ChatServerSocket(User user, Logger logger, IController controller) {
		this.isRunning = true;
		this.user = user;
		this.logger = logger;
		this.controller = controller;
	}

	public void closeServerSocket() {
		isRunning = false;

		logger.info("[chat-server-socket]: [" + user.getUsername() + "] chat-server exit");
	}

	@Override
	public void run() {
		Socket userSocket = user.getUserSocket();
		try {
			InputStream is = userSocket.getInputStream();
			while (isRunning) {
				int data;
				StringBuilder builder = new StringBuilder();

				while ((data = is.read()) != -1) {
					builder.append((char) data);
					if (data == '}') break;
				}

				logger.info("[chat-server-socket]: successfully read the inputStream, and the json is " + builder.toString());
				JSONObject json = (JSONObject) JSON.parse(builder.toString());
				int type = json.getInteger(JSONType.TYPE);
				if (type == JSONType.TYPE_MESSAGE) {
					String message = (String) json.get(JSONType.MESSAGE);
					String username = user.getUsername();
					String jsonString = JSONParser.createMessageJSONString(username, message);
					controller.sendToEveryOne(jsonString);
				} else if (type == JSONType.TYPE_LEAVE) {
					controller.removeUserByName(user.getUsername());
				}
			}

		} catch (Exception e) {
			logger.error("[chat-server-socket]: catch Exception: " + e + ", will remove the user in model");

			controller.removeUserByName(user.getUsername());
		}
	}

}
