package view.main;

import control.IController;
import control.main.IMainController;
import domain.Group;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 14:26
 */
public class AddGroupFormGUI extends JFrame {

	public static final int DEFAULT_WEIGTH = 300;
	public static final int DEFAULT_HEIGHT = 180;
	public static final int DEFAULT_X_LOCATION = 500;
	public static final int DEFAULT_Y_LOCATION = 100;

	private Logger logger;
	private IController controller;

	public JTextField getPort() {
		return port;
	}

	public JTextField getGroupName() {
		return groupName;
	}

	private JTextField port;
	private JTextField groupName;
	private JButton summit;

	public AddGroupFormGUI(Logger logger, IController controller) throws HeadlessException {
		this.logger = logger;
		this.controller = controller;

		defaultSetting();
		addFormPanel();
		addListener();
	}

	private void addListener() {
		summit.addActionListener(e -> {
			controller.addGroupGUISummit();
			this.setVisible(false);
		});
	}

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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private void addFormPanel() {
		JPanel inner = new JPanel(new GridLayout(2, 1));

		port = new JTextField();
		port.setBorder(BorderFactory.createTitledBorder("port"));

		groupName = new JTextField();
		groupName.setBorder(BorderFactory.createTitledBorder("group name"));

		inner.add(port);
		inner.add(groupName);

		summit = new JButton("summit~");

		add(inner, BorderLayout.NORTH);
		add(summit);
	}

	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	public void open() {
		setVisible(true);
	}

	public void close() {
		setVisible(false);
	}
}
