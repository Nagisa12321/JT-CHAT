package control.group;


import control.IController;
import domain.Group;
import domain.User;
import model.group.IGroupModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.group.IGroupView;
import view.group.UserInformationGUI;

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
public class ServerGroupController implements IGroupController {

	private IGroupModel model;
	private IGroupView view;
	private Logger logger;

	public ServerGroupController(Logger logger, IGroupModel model, IGroupView view) {
		this.model = model;
		this.logger = logger;
		this.view = view;
	}


	@Override
	public void getGroupInformation(String groupName) {
		view.setGroupName(groupName);
		view.update();
		view.open();
	}

	@Override
	public void openServer(String groupName, int port, IController controller) {
		ChatGroupServer chatGroupServer = new ChatGroupServer(logger);
		chatGroupServer.setController(controller);
		chatGroupServer.setPort(port);

		new Thread(chatGroupServer).start();

		model.addGroupServer(groupName, chatGroupServer);
	}

	@Override
	public void addGroupGUISummit(Group group, IController controller) {
		// TODO: 2021/4/23 开启群组服务器
		ChatGroupServer chatGroupServer = new ChatGroupServer(logger);
		chatGroupServer.setController(controller);
		chatGroupServer.setPort(group.getPort());

		new Thread(chatGroupServer).start();

		model.addGroupServer(group.getGroupName(), chatGroupServer);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServerGroupController{");
		sb.append("model=").append(model);
		sb.append(", view=").append(view);
		sb.append('}');
		return sb.toString();
	}

//	@Override
//	public void openServer() {
//		try {
//			String port = view.getPort().getText();
//			server.setPort(Integer.parseInt(port));
//			new Thread(server).start();
//
//			view.openMessage("you open the server and the port is [" + port + "].");
//			view.getCloseServer().setEnabled(true);
//			view.getOpenServer().setEnabled(false);
//		} catch (NumberFormatException e) {
//			view.openMessage("the port is iligeal!");
//		}
//	}
//
	@Override
	public void closeServer() {
		logger.debug("[ServerGroupController]: will close the server");
		removeAllUser();

		for (String groupName : model.getGroupNameList()) {
			model.getGroupServer(groupName).closeServer();
			model.getGroupServerSockets(groupName).values().
					forEach(ChatGroupServerSocket::closeServerSocket);
		}

		view.openMessage("you closed the server");
	}


	@Override
	public void sendToEveryOne(String json, String groupName) {
		logger.debug("[ServerGroupController]: will send to every one.");
		Map<String, ChatGroupServerSocket> groupServerSockets = model.getGroupServerSockets(groupName);

		Collection<ChatGroupServerSocket> chatGroupServerSockets = groupServerSockets.values();
		try {
			for (ChatGroupServerSocket serverSocket : chatGroupServerSockets) {
				MessageUtil.sendWithOutputStream(json, serverSocket.getUser().getSocket().getOutputStream());
			}
		} catch (IOException e) {
			logger.error("[ServerGroupController]: " + e);
		}
	}

	@Override
	public void sendToUserByName(String json, String groupName, String username) {
		Map<String, ChatGroupServerSocket> groupServerSockets = model.getGroupServerSockets(groupName);
		ChatGroupServerSocket chatGroupServerSocket = groupServerSockets.get(username);
		User user = chatGroupServerSocket.getUser();
		try {
			MessageUtil.sendWithOutputStream(json, user.getSocket().getOutputStream());
		} catch (IOException e) {
			logger.error("[ServerGroupController]: " + e);
		}
	}


//	@Override
//	public void removeUserByName(String username) {
//		model.removeUserByName(username);
//		String jsonString = JSONParser.createSystemMessageJSONString("[" + username + "] has leave the server :<");
//		sendToEveryOne(jsonString);
//	}

	@Override
	public void removeAllUser() {
		String jsonString = JSONParser.createSystemMessageJSONString("the server has been closed, every one must leave :<");

		// 通知每个用户服务器已经关闭
		for (String groupName : model.getGroupNameList()) {
			sendToEveryOne(groupName, jsonString);
			sendToEveryOne(groupName, JSONParser.createServerOverJSONString());
			Map<String, ChatGroupServerSocket> groupServerSockets = model.getGroupServerSockets(groupName);
			for (ChatGroupServerSocket chatGroupServerSocket : groupServerSockets.values()) {
				chatGroupServerSocket.closeServerSocket();
			}
		}

	}

	@Override
	public void kickUserByName(String groupName, String username) {
		// 告知客户端: "你被提出了"
		String json = JSONParser.createGMKickYouJSONString();
		sendToUserByName(json, groupName, username);

		// 要关闭用户的套接字线程
		shutDownGroupSocket(groupName, username);

		// 通知其他用户他被踢出了
		String jsonString = JSONParser.createSystemMessageJSONString("the user [" + username + "] has been kick by [=== GM ===]");
		sendToEveryOne(jsonString, groupName);
	}

	@Override
	public void getUserInformation(String groupName, String username) {
		User user = model.getGroupServerSockets(groupName).get(username).getUser();
		UserInformationGUI userInformationGUI = new UserInformationGUI(this, user, logger);
		userInformationGUI.open();
	}

//	@Override
//	public boolean checkUser(String username, OutputStream os) {
//		Map<String, User> users = model.getUsers();
//		String json;
//		boolean result;
//		if (users.containsKey(username)) {
//			json = JSONParser.createCheckUserJSONString(true);
//			result = false;
//		} else {
//			json = JSONParser.createCheckUserJSONString(false);
//			result = true;
//		}
//		sendWithOutputStream(json, os);
//		return result;
//	}

	@Override
	public void userAddGroup(String groupName, User user, IController controller, int port) {
		logger.info("user [" + user.getUsername() + "] add the group [" + groupName + "]");

		String jsonString = JSONParser.createSystemMessageJSONString("[" + user.getUsername() + "] has join the server :)");
		sendToEveryOne(jsonString, groupName);

		// 开启用户聊天窗口一对一链接线程
		ChatGroupServerSocket chatGroupServerSocket = new ChatGroupServerSocket(groupName, user, logger, controller);
		model.addGroupServerSocket(groupName, chatGroupServerSocket);

		new Thread(chatGroupServerSocket).start();

//		// TODO: 2021/4/23 开启群组服务器
//		ChatGroupServer chatGroupServer = new ChatGroupServer(logger);
//		chatGroupServer.setPort(port);
//
//		new Thread(chatGroupServer).start();
//
//		model.addGroupServer(groupName, chatGroupServer);
	}

	@Override
	public void shutDownGroupSocket(String groupName, String username) {
		logger.info("user [" + username + "] in [" + groupName + "]'s group server socket has been shut down.");
		ChatGroupServerSocket chatGroupServerSocket = model.getGroupServerSockets(groupName).get(username);
		chatGroupServerSocket.closeServerSocket();
	}

}
