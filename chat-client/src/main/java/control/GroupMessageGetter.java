package control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.Conversation;
import org.apache.log4j.Logger;
import util.JSONType;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/22 23:40
 */
public class GroupMessageGetter implements Runnable {

	private boolean running;
	private Logger logger;
	private IController controller;
	private Socket socket;

	public GroupMessageGetter(Socket socket, Logger logger, IController controller) {
		this.socket = socket;
		this.logger = logger;
		this.controller = controller;
		this.running = true;
	}

	public void close() {
		this.running = false;
		logger.info("[GroupMessageGetter]: close..");
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		logger.info("[group-message-getter]: start to running!");

		while (running) {
			try {
				InputStream is = socket.getInputStream();
				int data;
				StringBuilder builder = new StringBuilder();

				while ((data = is.read()) != -1) {
					builder.append((char) data);
					if (data == '}') break;
				}

				logger.info("[group-message-getter]: have accept the json :" + builder.toString());

				JSONObject json = (JSONObject) JSON.parse(builder.toString());
				int type = json.getInteger(JSONType.TYPE);
				if (type == JSONType.TYPE_MESSAGE) {
					Conversation conversation = toConversation(json);
					controller.addConversation(conversation);
				} else if (type == JSONType.TYPE_KICK) {
					controller.beenKicked();
					break;
				}

			} catch (IOException e) {
				logger.error("[chat-message-getter]: " + e);
				controller.serverClosed();
				break;
			}
		}
	}


	// 变成会话对象
	private Conversation toConversation(JSONObject messageJson) {
		Conversation conversation = new Conversation();

		String username = (String) messageJson.get(JSONType.USER_NAME);
		String time = (String) messageJson.get(JSONType.TIME);
		String value = (String) messageJson.get(JSONType.VALUE);

		conversation.setTimestamp(Timestamp.valueOf(time));
		conversation.setUsername(username);
		conversation.setValue(value);

		return conversation;
	}

}
