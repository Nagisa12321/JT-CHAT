package view.login;

import control.IController;
import control.login.ILoginController;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:51
 */
public class LoginGUI extends JFrame implements ILoginView{

	public static final int DEFAULT_WEIGTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_X_LOCATION = 250;
	public static final int DEFAULT_Y_LOCATION = 100;
	private final Logger logger;
	private IController controller;
	private ILoginController loginController;
	// 链接服务器
	private JButton connect;
	// IP
	private JTextField ip;
	// Port
	private JTextField port;
	// name
	private JTextField username;

	public LoginGUI(Logger logger) throws HeadlessException {
		this.logger = logger;
		defaultSetting();
		addChatPanel();
		addListener();
		debug();
	}

	private void debug() {
		ip.setText("localhost");
		port.setText("1234");
		username.setText("ccc");
	}

	private void addListener() {
		connect.addActionListener(e -> {
			loginController.connect();
		});
	}

	public void open() {
		setVisible(true);
	}

	// 默认设置
	private void defaultSetting() {

		// 设置图标
		ImageIcon imageIcon = new ImageIcon("src/main/resources/img/pic.jpg");
		setIconImage(imageIcon.getImage());

		// 设置标题
		setTitle("--JT-CHAT-Login--                - by jtchen");
		// 设置方位
		setLocation(DEFAULT_X_LOCATION, DEFAULT_Y_LOCATION);
		// 设置不可变大小
		setResizable(false);
		// 设置默认长宽
		setSize(DEFAULT_WEIGTH / 3, DEFAULT_HEIGHT / 3);
		// 设置关闭动作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addChatPanel() {
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		JPanel down = new JPanel();
		left.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH / 3 * 0.3), (int) (DEFAULT_HEIGHT / 3 * 0.8)));
		right.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH / 3 * 0.7), (int) (DEFAULT_HEIGHT / 3 * 0.8)));
		down.setPreferredSize(new Dimension(DEFAULT_WEIGTH / 3, (int) (DEFAULT_HEIGHT / 15)));

		connect = new JButton("connect");
		connect.setPreferredSize(new Dimension(DEFAULT_WEIGTH / 4, (int) (DEFAULT_HEIGHT / 19)));
		ip = new JTextField();
		port = new JTextField();
		username = new JTextField();
		ip.setPreferredSize(new Dimension(150, 30));
		port.setPreferredSize(new Dimension(150, 30));
		username.setPreferredSize(new Dimension(150, 30));

		JLabel ipLabel = new JLabel("ip:", SwingConstants.RIGHT);

		JLabel portLabel = new JLabel("port:", SwingConstants.RIGHT);
		JLabel usernameLabel = new JLabel("username:", SwingConstants.RIGHT);

		ipLabel.setPreferredSize(new Dimension(70, 30));
		portLabel.setPreferredSize(new Dimension(70, 30));
		usernameLabel.setPreferredSize(new Dimension(70, 30));

		left.add(ipLabel);
		left.add(portLabel);
		left.add(usernameLabel);
		right.add(ip);
		right.add(port);
		right.add(username);
		down.add(connect);

		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(down, BorderLayout.SOUTH);
	}

	@Override
	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public JButton getConnect() {
		return connect;
	}

	@Override
	public JTextField getIp() {
		return ip;
	}

	@Override
	public JTextField getPort() {
		return port;
	}

	@Override
	public JTextField getUsername() {
		return username;
	}

	@Override
	public void connectSuccess() {
		setVisible(false);
	}

	public void setLoginController(ILoginController loginController) {
		this.loginController = loginController;
	}

	public IController getController() {
		return controller;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
}