package control;


import domain.User;
import model.IModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import view.IView;
import view.UserInformationGUI;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:54
 */
public class ServerController implements IController {

	private IModel model;
	private IView view;
	private Logger logger;
	private ChatServer server;

	public ServerController(Logger logger, IModel model, IView view) {
		this.model = model;
		this.logger = logger;
		this.view = view;
		initView();
	}


	private void initView() {
		view.getCloseServer().setEnabled(false);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServerController{");
		sb.append("model=").append(model);
		sb.append(", view=").append(view);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void openServer() {
		try {
			String port = view.getPort().getText();
			server = new ChatServer(Integer.parseInt(port), logger, this);
			new Thread(server).start();

			view.openMessage("you open the server and the port is [" + port + "].");
			view.getCloseServer().setEnabled(true);
			view.getOpenServer().setEnabled(false);
		} catch (NumberFormatException e) {
			view.openMessage("the port is iligeal!");
		}
	}

	@Override
	public void closeServer() {

		logger.debug("[ServerController]: will close the server");
		server.closeServer();
		view.openMessage("you closed the server");
		view.getCloseServer().setEnabled(false);
		view.getOpenServer().setEnabled(true);

	}

	@Override
	public void addUser(User user) {
		model.addUser(user);
		String jsonString = JSONParser.createSystemMessageJSONString("[" + user.getUsername() + "] has join the server :)");
		sendToEveryOne(jsonString);
	}

	@Override
	public void sendToEveryOne(String json) {
		logger.debug("[ServerController]: will send to every one.");
		Map<String, User> users = model.getUsers();
		Collection<User> values = users.values();
		try {
			for (User user : values) {
				sendWithOutputStream(json, user.getUserSocket().getOutputStream());
			}
		} catch (IOException e) {
			logger.error("[ServerController]: " + e);
		}
	}

	@Override
	public void sendToUserByName(String json, String username) {
		Map<String, User> users = model.getUsers();
		User user = users.get(username);
		try {
			sendWithOutputStream(json, user.getUserSocket().getOutputStream());
		} catch (IOException e) {
			logger.error("[ServerController]: " + e);
		}
	}


	@Override
	public void removeUserByName(String username) {
		model.removeUserByName(username);
		String jsonString = JSONParser.createSystemMessageJSONString("[" + username + "] has leave the server :<");
		sendToEveryOne(jsonString);
	}

	@Override
	public void removeAllUser() {
		String jsonString = JSONParser.createSystemMessageJSONString("the server has been closed, every one must leave :<");
		sendToEveryOne(jsonString);
		sendToEveryOne(JSONParser.createServerOverJSONString());
		model.removeAllUser();
	}

	@Override
	public void kickUserByName(String username) {
		// 告知客户端: "你被提出了"
		String json = JSONParser.createGMKickYouJSONString();
		sendToUserByName(json, username);

		// 操作模型, 同时模型会影响view
		model.removeUserByName(username);

		// 要关闭用户的套接字线程
		server.closeSocketByName(username);

		// 通知其他用户他被踢出了
		String jsonString = JSONParser.createSystemMessageJSONString("the user [" + username + "] has been kick by [=== GM ===]");
		sendToEveryOne(jsonString);
	}

	@Override
	public void getUserInformation(String username) {
		User user = model.getUsers().get(username);
		UserInformationGUI userInformationGUI = new UserInformationGUI(this, user, logger);
		userInformationGUI.open();
	}

	@Override
	public boolean checkUser(String username, OutputStream os) {
		Map<String, User> users = model.getUsers();
		String json;
		boolean result;
		if (users.containsKey(username)) {
			json = JSONParser.createCheckUserJSONString(true);
			result = false;
		} else {
			json = JSONParser.createCheckUserJSONString(false);
			result = true;
		}
		sendWithOutputStream(json, os);
		return result;
	}

	private void sendWithOutputStream(String json, OutputStream os) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(os);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			logger.debug("[ServerController]: send failed: " + e);
		}
	}
}
