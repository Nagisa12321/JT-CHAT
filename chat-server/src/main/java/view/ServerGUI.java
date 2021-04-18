package view;

import control.IController;
import domain.User;
import model.IModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:26
 */
public class ServerGUI extends JFrame implements IView {

	public static final int DEFAULT_WEIGTH = 400;
	public static final int DEFAULT_HEIGHT = 300;
	public static final int DEFAULT_X_LOCATION = 500;
	public static final int DEFAULT_Y_LOCATION = 100;
	private final Logger logger;
	private IModel model;
	private IController controller;

	// 在线人员
	private JList<String> list;
	// 端口号
	private JTextField port;
	// 开启服务器
	private JButton openServer;
	// 关闭服务器
	private JButton closeServer;
	// 用户详细信息
	private JButton userInformation;
	// 踢出用户
	private JButton kickUser;

	public ServerGUI(Logger logger) throws HeadlessException {
		this.logger = logger;
		defaultSetting();
		addServerPanel();
		addListener();
		debug();
	}

	private void debug() {
		port.setText("1234");
	}

	public void open() {
		setVisible(true);
	}

	@Override
	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	// 默认设置
	private void defaultSetting() {

		// 设置图标
		ImageIcon imageIcon = new ImageIcon("src/main/resources/img/pic.jpg");
		setIconImage(imageIcon.getImage());

		// 设置标题
		setTitle("--JT-CHAT-Server--    - by jtchen");
		// 设置方位
		setLocation(DEFAULT_X_LOCATION, DEFAULT_Y_LOCATION);
		// 设置不可变大小
		setResizable(false);
		// 设置默认长宽
		setSize(DEFAULT_WEIGTH, DEFAULT_HEIGHT);
		// 设置关闭动作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addServerPanel() {
		JPanel listPanel = new JPanel();
		JPanel settingPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(DEFAULT_WEIGTH / 2, DEFAULT_HEIGHT));
		settingPanel.setPreferredSize(new Dimension(DEFAULT_WEIGTH / 2, DEFAULT_HEIGHT));

		list = new JList<>();
		list.setPreferredSize(new Dimension(DEFAULT_WEIGTH / 2, DEFAULT_HEIGHT));

		port = new JTextField();
		port.setPreferredSize(new Dimension(120, 30));
		openServer = new JButton("open server");
		closeServer = new JButton("close server");
		kickUser = new JButton("kick this user");
		userInformation = new JButton("user information");

		listPanel.add(new JLabel("user list"));
		listPanel.add(list);
		settingPanel.add(new JLabel("port: "));
		settingPanel.add(port);
		settingPanel.add(openServer);
		settingPanel.add(closeServer);
		settingPanel.add(kickUser);
		settingPanel.add(userInformation);

		add(listPanel, BorderLayout.WEST);
		add(settingPanel, BorderLayout.EAST);
	}

	private void addListener() {
		openServer.addActionListener(e -> controller.openServer());

		closeServer.addActionListener(e -> controller.closeServer());

		kickUser.addActionListener(e -> {
			String username = list.getSelectedValue();
			if (username != null)
				controller.kickUserByName(username);
		});

		userInformation.addActionListener(e -> {
			String username = list.getSelectedValue();
			if (username != null)
				controller.getUserInformation(username);
		});
	}

	public JTextField getPort() {
		return port;
	}

	public JButton getOpenServer() {
		return openServer;
	}

	public JButton getCloseServer() {
		return closeServer;
	}

	@Override
	public void update() {
		Map<String, User> users = model.getUsers();
		list.setListData(users.keySet().toArray(new String[0]));
	}

	public void setModel(IModel model) {
		this.model = model;
		model.rigisterObserver(this);
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
}
