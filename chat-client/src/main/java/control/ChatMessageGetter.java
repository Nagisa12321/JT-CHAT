package control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import control.chat.IChatController;
import control.login.ILoginController;
import domain.Conversation;
import org.apache.log4j.Logger;
import util.JSONType;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 16:49
 */
public class ChatMessageGetter implements Runnable {

	private InputStream is;
	private final Logger logger;
	private IController controller;

	public ChatMessageGetter(Logger logger) {
		this.logger = logger;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {

		while (true) {
			try {
				int data;
				StringBuilder builder = new StringBuilder();

				while ((data = is.read()) != -1) {
					builder.append((char) data);
					if (data == '}') break;
				}

				logger.info("[chat-message-getter]: have accept the json :" + builder.toString());

				JSONObject json = (JSONObject) JSON.parse(builder.toString());
				int type = json.getInteger(JSONType.TYPE);
				if (type == JSONType.TYPE_MESSAGE) {
					Conversation conversation = toConversation(json);
					controller.addConversation(conversation);
				} else if (type == JSONType.TYPE_SERVER_BEEN_SHUTDOWN) {
					controller.serverClosed();
					break;
				} else if (type == JSONType.TYPE_KICK) {
					controller.beenKicked();
					break;
				} else if (type == JSONType.TYPE_CHECK_USERS) {
					boolean exist = json.getBoolean(JSONType.EXIST);
					if (exist) {
						controller.connectFailed();
						break;
					} else controller.connectSuccess();
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

	public void setController(IController controller) {
		this.controller = controller;
	}
}
