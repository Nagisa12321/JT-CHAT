package control.login;

import control.ChatMessageGetter;
import domain.User;
import model.login.ILoginModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.login.ILoginView;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:55
 */
public class LoginController implements ILoginController {

	private ILoginView view;

	private ILoginModel model;

	private Logger logger;

	private Socket socket;

	private ChatMessageGetter getter;

	public LoginController(ILoginView view, ILoginModel model, Logger logger, ChatMessageGetter getter) {
		this.view = view;
		this.logger = logger;
		this.model = model;
		this.getter = getter;
	}

	@Override
	public void connect() {
		String ip = view.getIp().getText();
		String port = view.getPort().getText();
		String username = view.getUsername().getText();
		if (!check(ip, port, username)) return;
		try {
			socket = new Socket(ip, Integer.parseInt(port));
			// 开启getter线程

			getter.setIs(socket.getInputStream());
			Thread thread = new Thread(getter);
			thread.start();

			// 查询用户名是否重复
			sendUserInformation();
		} catch (IOException e) {
			logger.info("[controller]: connect failed:  " + e.getMessage());
			view.openMessage("connect failed.");
		} catch (NumberFormatException e) {
			view.openMessage("connect failed. the port must be a number!");
		}

	}

	@Override
	public void connectSuccess() {
		logger.info("[controller]: connect success.");
		model.setUser(new User(view.getUsername().getText(), socket));
		view.openMessage("connect success.");
		view.connectSuccess();
	}

	@Override
	public void connectFailed() {
		view.openMessage("connect failed. the username can not be same ");
	}

	@Override
	public void openView() {
		view.open();
	}

	private void sendUserInformation() throws IOException {
		String username = view.getUsername().getText();
		String usernameJson = JSONParser.createUserInformationJSONString(username);

		MessageUtil.sendJSON(usernameJson, socket.getOutputStream());
	}

	private boolean check(String ip, String port, String username) {
		// 只检查是否为空..
		if ("".equals(ip) || "".equals(port) || "".equals(username)) {
			view.openMessage("the ip/port/username must not be empty");
			return false;
		}
		return true;
	}

}
