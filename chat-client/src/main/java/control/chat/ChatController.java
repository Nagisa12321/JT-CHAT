package control.chat;

import control.GroupMessageGetter;
import control.IController;
import domain.Conversation;
import domain.Group;
import domain.User;
import model.chat.IChatModel;
import model.login.ILoginModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.chat.ChatGUI;
import view.chat.IChatView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:48
 */
public class ChatController implements IChatController {

	private final Logger logger;
	private final IChatModel model;
	private final ILoginModel loginModel;
	private IChatView view;


	public ChatController(Logger logger, IChatModel model, ILoginModel loginModel) {
		this.loginModel = loginModel;
		this.logger = logger;
		this.model = model;
	}

	public void initView() {
		view.getMessageArea().setEditable(true);
		view.getLeave().setEnabled(true);
		view.getArea().setEditable(false);
	}

	@Override
	public String toString() {
		return "ChatController{" + "model=" + model +
				", view=" + view +
				'}';
	}


	private void setButtonConnected() {
		view.getMessageArea().setEditable(true);
		view.getLeave().setEnabled(true);
	}

	private void sendMessage(String groupName) throws IOException {
		String username = model.getUser().getUsername();
		String message = view.getMessageArea().getText();
		String messageJSON = JSONParser.createUserIntroductionJSONString(username, message);

		Socket socket = model.getGroupMessageGetter(groupName).getSocket();
		MessageUtil.sendJSON(messageJSON, socket.getOutputStream());

	}


	@Override
	public void send() {
		String groupName = view.getGroupName();
		if (!model.isConnected(groupName)) {
			view.openMessage("you have not connected the server, please dont do this.");
		} else {
			try {
				sendMessage(groupName);
				view.getMessageArea().setText("");
			} catch (IOException e) {
				logger.info("[controller]: leave failed:  " + e.getMessage());
				view.openMessage("leave failed.");
			}
		}
	}

	@Override
	public void setUser(User user) {
		if (user == null) return;

		model.setUser(user);
	}

	@Override
	public void serverClosed() {
		if (view == null) return ;
		view.openMessage("the server is closed~ ");
		model.setConnected(view.getGroupName(), false);
		view.close();

		// close every group getter
		model.getAllGroupGetters().forEach(GroupMessageGetter::close);

		setButtonUnconnected();
	}

	@Override
	public void addConversation(Conversation conversation) {
		model.addConversation(view.getGroupName(), conversation);
	}

	@Override
	public void leave() {
		if (!model.isConnected(view.getGroupName())) {
			view.openMessage("you haven't connect the server, why leave?");
		} else {
			try {
				model.setConnected(view.getGroupName(), false);
				Socket socket = model.getUser().getSocket();
				MessageUtil.sendJSON(JSONParser.createUserLeaveJSONString(), socket.getOutputStream());

				view.openMessage("you just leave.. @_@");
				view.close();
				setButtonUnconnected();
			} catch (IOException e) {
				logger.info("");
			}
		}
	}

	private void setButtonUnconnected() {
		view.getMessageArea().setEditable(false);
		view.getLeave().setEnabled(false);
	}

	@Override
	public void beenKicked() {
		view.openMessage("you have been kicked... :(");
		model.setConnected(view.getGroupName(), false);
		view.close();
		setButtonUnconnected();
	}

	@Override
	public void connectSuccess() {
		setButtonConnected();
		view.open();
		model.setConnected(view.getGroupName(), true);
		model.setUser(loginModel.getUser());
	}

	@Override
	public void newGUI(String groupName, IController controller) {
		ChatGUI chatGUI = new ChatGUI(logger, groupName);
		chatGUI.setController(controller);
		chatGUI.setChatController(this);
		chatGUI.setModel(model);

		// 注册为已经链接
		model.setConnected(groupName, true);
		setView(chatGUI);
		initView();
		chatGUI.open();
	}

	@Override
	public void joinTheGroup(String groupName, int port, IController controller) {
		InetAddress inetAddress = model.getUser().getSocket().getInetAddress(); // ip

		try {
			Socket socket = new Socket(inetAddress, port);
			GroupMessageGetter getter = new GroupMessageGetter(socket, logger, controller);
			new Thread(getter).start(); // 开启线程

			// 向群组服务器发送通知表示加入群组, 让其开启一对一线程
			String userAddGroupJSONString = JSONParser.createUserAddGroupJSONString(groupName, model.getUser().getUsername());
			MessageUtil.sendJSON(userAddGroupJSONString, socket.getOutputStream());
			Group group = new Group();
			group.setGroupName(groupName);
			group.setPort(port);
			model.addGroup(group);
			model.addGroupMessageGetter(groupName, getter);
			newGUI(groupName, controller);
		} catch (IOException e) {
			// 链接服务器错误, 要发信息给服务器, 说明这个端口无法连接 ?
			// 待完善

			// view.openMessage("could not join this group");
		}
	}

	public void setView(IChatView view) {
		this.view = view;
	}
}
