package control.chat;

import domain.Conversation;
import model.chat.IChatModel;
import model.login.ILoginModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.chat.IChatView;

import java.io.IOException;
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
	private final IChatView view;


	public ChatController(Logger logger, IChatModel model, ILoginModel loginModel, IChatView view) {
		this.loginModel = loginModel;
		this.logger = logger;
		this.model = model;
		this.view = view;

		initView();
	}

	public void initView() {
		view.getMessageArea().setEditable(false);
		view.getLeave().setEnabled(false);
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

	private void sendMessage() throws IOException {
		String username = model.getUser().getUsername();
		String message = view.getMessageArea().getText();
		String messageJSON = JSONParser.createUserIntroductionJSONString(username, message);

		Socket socket = model.getUser().getSocket();
		MessageUtil.sendJSON(messageJSON, socket.getOutputStream());
	}


	@Override
	public void send() {
		if (!model.isConnected()) {
			view.openMessage("you have not connected the server, please dont do this.");
		} else {
			try {
				sendMessage();
				view.getMessageArea().setText("");
			} catch (IOException e) {
				logger.info("[controller]: leave failed:  " + e.getMessage());
				view.openMessage("leave failed.");
			}
		}
	}

	@Override
	public void serverClosed() {
		view.openMessage("the server is closed~ ");
		model.setConnected(false);
		view.closeConnect();
		setButtonUnconnected();
	}

	@Override
	public void addConversation(Conversation conversation) {
		model.addConversation(conversation);
	}

	@Override
	public void leave() {
		if (!model.isConnected()) {
			view.openMessage("you haven't connect the server, why leave?");
		} else {
			try {
				model.setConnected(false);
				Socket socket = model.getUser().getSocket();
				MessageUtil.sendJSON(JSONParser.createUserLeaveJSONString(), socket.getOutputStream());

				view.openMessage("you just leave.. @_@");
				view.closeConnect();
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
		model.setConnected(false);
		view.closeConnect();
		setButtonUnconnected();
	}

	@Override
	public void connectSuccess() {
		setButtonConnected();
		view.open();
		model.setConnected(true);
		model.setUser(loginModel.getUser());
	}

}
