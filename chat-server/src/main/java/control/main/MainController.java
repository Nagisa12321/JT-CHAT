package control.main;

import control.IController;
import control.group.ChatGroupServerSocket;
import domain.Group;
import domain.User;
import model.main.IMainModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.group.ServerGUI;
import view.main.AddGroupFormGUI;
import view.main.IMainView;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 14:08
 */
public class MainController implements IMainController {

	private IMainModel model;
	private IMainView view;
	private Logger logger;
	private ChatMainServer server;
	private AddGroupFormGUI addGUI;
	private ServerGUI serverGUI;


	public MainController(IMainModel model, IMainView view, Logger logger, ChatMainServer server) {
		this.model = model;
		this.view = view;
		this.logger = logger;
		initView();
	}

	private void initView() {
		setButtonClosed();
	}

	public void setServer(ChatMainServer server) {
		this.server = server;
	}

	@Override
	public void open() {
		view.open();
	}

	@Override
	public void userAskGroup(String groupName, User user) throws IOException {
		// 查询用户要加入的群组port, 通知用户
		int port = model.getGroupByName(groupName).getPort();
		String replyGroupPort = JSONParser.createReplyGroupPort(port, groupName);

		MessageUtil.sendWithOutputStream(replyGroupPort, user.getSocket().getOutputStream());

	}

	@Override
	public int userAddGroup(String groupName) {
		return model.getGroupByName(groupName).getPort();
	}

	private void setButtonOpen() {
		view.getCloseServer().setEnabled(true);
		view.getAddGroup().setEnabled(true);
		view.getGroupInformation().setEnabled(true);
		view.getOpenServer().setEnabled(false);
	}

	private void setButtonClosed() {
		view.getCloseServer().setEnabled(false);
		view.getAddGroup().setEnabled(false);
		view.getGroupInformation().setEnabled(false);
		view.getOpenServer().setEnabled(true);
	}


	@Override
	public void closeServer() {
		if (model.isClose()) {
			view.openMessage("the model is closed, why close again?");
			return;
		}
		model.close();
		server.closeServer();

		Collection<ChatMainServerSocket> chatMainServerSockets = model.getChatMainServerSockets();
		try {
			for (ChatMainServerSocket chatMainServerSocket : chatMainServerSockets) {
				// tell the users
				User user = chatMainServerSocket.getUser();
				String serverOverJSONString = JSONParser.createServerOverJSONString();
				MessageUtil.sendWithOutputStream(serverOverJSONString, user.getSocket().getOutputStream());

				// close the server sockets
				chatMainServerSocket.closeServerSocket();
			}
		} catch (IOException e) {
			view.openMessage("close server failed");
			return;
		}


		view.openMessage("close server success");
		setButtonClosed();
	}

	@Override
	public void openServer() {
		if (!model.isClose()) {
			view.openMessage("the model is open, why open again? ");
			return;
		}
		try {
			int port = Integer.parseInt(view.getPort().getText());
			model.start();
			// 启动主面板服务器
			server.setPort(port);
			new Thread(server).start();

			view.openMessage("you open the server and the port is [" + port + "].");
			setButtonOpen();
		} catch (NumberFormatException e) {
			view.openMessage("the port must be a number... ");
		}
	}

	@Override
	public void addGroup(IController controller) {
		if (model.isClose()) {
			view.openMessage("the model is closed, you can not add any group!");
			return;
		}
		addGUI = new AddGroupFormGUI(logger, controller);
		addGUI.open();
	}

	@Override
	public void addGroup(Group group) {
		if (model.isClose()) {
			view.openMessage("the model is closed, you can not add any group!");
			return;
		}
		model.addGroup(group);

	}

	// 处理用户信息
	@Override
	public void checkUser(User user, IController controller) {
		String json;
		if (model.containsUser(user.getUsername())) {
			json = JSONParser.createCheckUserJSONString(true);
		} else {
			json = JSONParser.createCheckUserJSONString(false);

			// 新建用户一对一线程, 并在模型中更新
			ChatMainServerSocket chatMainServerSocket = new ChatMainServerSocket(user, logger, controller);
			new Thread(chatMainServerSocket).start();
			model.addChatMainServerSocket(chatMainServerSocket);
		}
		// 发送给用户链接成功/ 失败
		try {
			MessageUtil.sendWithOutputStream(json, user.getSocket().getOutputStream());
		} catch (IOException e) {
			logger.debug("[MainController]: " + e);
		}
	}

	@Override
	public void removeUserByName(String username) {
		model.removeUserByName(username);
	}

	@Override
	public void getGroupNameList(User user) {
		try {
			String port = String.valueOf(user.getSocket().getPort());
			String ip = user.getSocket().getInetAddress().toString();
			Collection<String> allGroups = model.getAllGroups();
			String getGroupNameListJSONString = JSONParser.createGetGroupNameListJSONString(new ArrayList<>(allGroups), ip, port);
			MessageUtil.sendWithOutputStream(getGroupNameListJSONString, user.getSocket().getOutputStream());
		} catch (IOException e) {
			logger.debug("[MainController]: " + e);
		}
	}

	@Override
	public Group addGroupGUISummit() {
		String groupName = addGUI.getGroupName().getText();
		if ("".equals(groupName)) {
			addGUI.openMessage("group name should not be empty.");
			return null;
		} else if (model.getGroupByName(groupName) != null) {
			addGUI.openMessage("the group name is exist, please try again");
			return null;
		}
		int port;
		try {
			port = Integer.parseInt(addGUI.getPort().getText());
			if (model.containPort(port)) {
				addGUI.openMessage("the port is exist, please try again");
				return null;
			}
		} catch (NumberFormatException numberFormatException) {
			addGUI.openMessage("port should be a number.");
			return null;
		}
		Group group = new Group();
		group.setPort(port);
		group.setGroupName(groupName);
		group.setCreator("=== GM ===");
		group.setCreateTime(new Date(System.currentTimeMillis()));

		addGroup(group);
		// 开启群组服务器
		return group;
	}

	public ServerGUI getServerGUI() {
		return serverGUI;
	}

	public void setServerGUI(ServerGUI serverGUI) {
		this.serverGUI = serverGUI;
	}
}
