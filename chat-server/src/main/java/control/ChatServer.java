package control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import org.apache.log4j.Logger;
import util.JSONType;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 18:21
 */
public class ChatServer implements Runnable {

	private final int port;
	private final Logger logger;
	private final IController controller;
	private final Map<String, ChatServerSocket> serverSockets;
	private boolean isRunning;

	public ChatServer(int port, Logger logger, IController controller) {
		this.isRunning = true;
		this.serverSockets = new HashMap<>();
		this.port = port;
		this.logger = logger;
		this.controller = controller;
	}

	// 整个服务器关闭
	public void closeServer() {
		isRunning = false;

		logger.debug("[chat-server]: server is interrupted, will send to every one the server is over");
		controller.removeAllUser();
		serverSockets.values().forEach(ChatServerSocket::closeServerSocket);
		logger.info("[chat-server]: chat-server exit");
	}

	// 关闭对某个用户的链接
	public void closeSocketByName(String username) {
		ChatServerSocket chatServerSocket = serverSockets.get(username);
		chatServerSocket.closeServerSocket();

		logger.info("[ChatServer]: user[" + username + "]'s chat-server-socket has been closed..");
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			// logger.info("[chat-server]: server is open, the port is " + port);
			// 每隔1s刷一次
			serverSocket.setSoTimeout(1000);
			while (isRunning) {
				Socket socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				// 获得用户信息
				int data;
				StringBuilder builder = new StringBuilder();

				while ((data = is.read()) != -1) {
					builder.append((char) data);
					if (data == '}') break;
				}

				logger.info("[chat-server]: successfully read the inputStream, and the json is " + builder.toString());
				JSONObject json = (JSONObject) JSON.parse(builder.toString());
				int type = json.getInteger(JSONType.TYPE);
				if (type == JSONType.TYPE_USER_INFORMATION) {
					String username = json.getString(JSONType.USER_NAME);
					// 检查用户信息是否合法
					if (controller.checkUser(username, socket.getOutputStream())) {
						User user = new User(username, socket);
						controller.addUser(user);

						ChatServerSocket chatServerSocket = new ChatServerSocket(user, logger, controller);
						Thread thread = new Thread(chatServerSocket);
						thread.start();
						serverSockets.put(username, chatServerSocket);
					}
				}
			}
		} catch (IOException e) {
			// logger.info("[chat-server]: " + e);
			run();
		}
	}
}
