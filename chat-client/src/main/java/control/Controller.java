package control;

import control.chat.IChatController;
import control.login.ILoginController;
import domain.Conversation;
import org.apache.log4j.Logger;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 12:46
 */
public class Controller implements IController {

	private IChatController chatController;
	private ILoginController loginController;
	private Logger logger;

	public Controller(IChatController chatController, ILoginController loginController, Logger logger) {
		this.chatController = chatController;
		this.loginController = loginController;
		this.logger = logger;
	}

	@Override
	public void connectSuccess() {
		loginController.connectSuccess();
		chatController.connectSuccess();
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
		loginController.openView();
	}
}
