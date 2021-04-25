package control.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import control.IController;
import domain.User;
import org.apache.log4j.Logger;
import util.JSONType;

import java.io.InputStream;
import java.net.Socket;

/**
 * 用户主界面和服务器的一对一链接
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 18:40
 */
public class ChatMainServerSocket implements Runnable {

	private IController controller;
	private User user;
	private Logger logger;
	private boolean isRunning;

	public User getUser() {
		return user;
	}

	public ChatMainServerSocket(User user, Logger logger, IController controller) {
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
		Socket userSocket = user.getSocket();
		try {
			InputStream is = userSocket.getInputStream();
			while (isRunning) {
				int data;
				StringBuilder builder = new StringBuilder();

				while ((data = is.read()) != -1) {
					builder.append((char) data);
					if (data == '}') break;
				}

				logger.info("[chat-main-server-socket]: successfully read the inputStream, and the json is " + builder.toString());
				JSONObject json = (JSONObject) JSON.parse(builder.toString());
				int type = json.getInteger(JSONType.TYPE);
				// 判断响应json
				if (type == JSONType.TYPE_GET_GROUP_NAMELIST) {
					controller.getGroupNameList(user);
				} else if (type == JSONType.TYPE_USER_ADD_GROUP) {
					// String username = json.getString(JSONType.USER_NAME);
					String groupName = json.getString(JSONType.GROUP_NAME);

					controller.userAskGroup(user, groupName, controller);
				}
			}

		} catch (Exception e) {
			logger.error("[chat-main-server-socket]: catch Exception: " + e + ", will remove the user in model");

			controller.removeUserByName(user.getUsername());
		}
	}

}
