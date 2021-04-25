package control;

import control.chat.IChatController;
import control.login.ILoginController;
import control.main.IMainController;
import domain.Conversation;
import domain.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 12:46
 */
public class Controller implements IController {

	private IChatController chatController;
	private ILoginController loginController;
	private IMainController mainController;
	private Logger logger;

	public Controller(IMainController mainController, IChatController chatController, ILoginController loginController, Logger logger) {
		this.chatController = chatController;
		this.mainController = mainController;
		this.loginController = loginController;
		this.logger = logger;
	}

	@Override
	public void connectSuccess() {
		mainController.connectSuccess(loginController.connectSuccess());
	}

	@Override
	public void connectFailed() {
		loginController.connectFailed();
	}

	@Override
	public void addConversation(Conversation conversation) {
		chatController.addConversation(conversation);
	}

	@Override
	public void serverClosed() {
		mainController.serverClosed();
		chatController.serverClosed();
		loginController.openView();
	}

	@Override
	public void beenKicked() {
		chatController.beenKicked();
		loginController.openView();
	}

	@Override
	public void start() {
		loginController.openView();
	}

	@Override
	public void leave() {
		chatController.leave();
	}

	@Override
	public void setGroupList(List<String> groupList, String ip, String port) {
		mainController.setGroupList(groupList);
		mainController.setIpAndPort(ip, port);
	}

	@Override
	public void joinTheGroup() {
		User user = mainController.joinTheGroup();
		chatController.setUser(user);
	}

	@Override
	public void joinTheGroup(String groupName, int port) {
		chatController.joinTheGroup(groupName, port, this);
	}
}
