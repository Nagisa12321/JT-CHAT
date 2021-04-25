package view.group;

import control.group.IGroupController;
import domain.User;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/16 14:20
 */
public class UserInformationGUI extends JFrame {
	public static final int DEFAULT_WEIGTH = 200;
	public static final int DEFAULT_HEIGHT = 100;
	public static final int DEFAULT_X_LOCATION = 500;
	public static final int DEFAULT_Y_LOCATION = 100;

	private IGroupController controller;
	private User user;
	private Logger logger;

	public UserInformationGUI(IGroupController controller, User user, Logger logger) throws HeadlessException {
		this.controller = controller;
		this.user = user;
		this.logger = logger;

		defaultSetting();
		addPanel();
	}

	public void open() {
		setVisible(true);
	}

	private void addPanel() {
		JPanel jPanel = new JPanel();

		JLabel ip = new JLabel("ip:");
		JLabel port = new JLabel("port");

		JLabel userIP = new JLabel(user.getSocket().getInetAddress().toString());
		JLabel userPort = new JLabel(String.valueOf(user.getSocket().getPort()));

		jPanel.add(ip);
		jPanel.add(userIP);
		jPanel.add(port);
		jPanel.add(userPort);

		add(jPanel);
	}

	// 默认设置
	private void defaultSetting() {

		// 设置图标
		ImageIcon imageIcon = new ImageIcon("src/main/resources/img/pic.jpg");
		setIconImage(imageIcon.getImage());

		// 设置标题
		setTitle("user information");
		// 设置方位
		setLocation(DEFAULT_X_LOCATION, DEFAULT_Y_LOCATION);
		// 设置不可变大小
		setResizable(false);
		// 设置默认长宽
		setSize(DEFAULT_WEIGTH, DEFAULT_HEIGHT);
		// 设置关闭动作
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
