package view.group;

import control.group.ChatGroupServerSocket;
import control.group.IGroupController;
import model.group.IGroupModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:26
 */
public class ServerGUI extends JFrame implements IGroupView {

	public static final int DEFAULT_WEIGTH = 400;
	public static final int DEFAULT_HEIGHT = 300;
	public static final int DEFAULT_X_LOCATION = 500;
	public static final int DEFAULT_Y_LOCATION = 100;
	private final Logger logger;
	private IGroupModel model;
	private IGroupController controller;
	private String groupName;

	// 在线人员
	private JList<String> list;
	// 用户详细信息
	private JButton userInformation;
	// 踢出用户
	private JButton kickUser;

	public ServerGUI(Logger logger) throws HeadlessException {
		this.logger = logger;
		defaultSetting();
		addServerPanel();
		addListener();
	}

	public void open() {
		setVisible(true);
		setTitle("[" + groupName + "]");
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

		kickUser = new JButton("kick this user");
		userInformation = new JButton("user information");

		listPanel.add(new JLabel("user list"));
		listPanel.add(list);
		settingPanel.add(new JLabel("port: "));
		settingPanel.add(kickUser);
		settingPanel.add(userInformation);

		add(listPanel, BorderLayout.WEST);
		add(settingPanel, BorderLayout.EAST);
	}

	private void addListener() {

		kickUser.addActionListener(e -> {
			String username = list.getSelectedValue();
			if (username != null)
				controller.kickUserByName(groupName, username);
		});

		userInformation.addActionListener(e -> {
			String username = list.getSelectedValue();
			if (username != null)
				controller.getUserInformation(groupName, username);
		});
	}

	@Override
	public void update() {
		Map<String, ChatGroupServerSocket> socketMap = model.getGroupServerSockets(groupName);
		if (socketMap == null) return ; // 没有值不用更新
 		Collection<ChatGroupServerSocket> serverSockets = socketMap.values();
		Vector<String> userNames = new Vector<>();
		serverSockets.forEach(chatGroupServerSocket -> userNames.add(chatGroupServerSocket.getUser().getUsername()));
		list.setListData(userNames);
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setController(IGroupController controller) {
		this.controller = controller;
	}

	public void setModel(IGroupModel model) {
		this.model = model;
		model.rigisterObserver(this);
	}

}
