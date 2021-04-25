package view.main;

import control.IController;
import control.main.IMainController;
import model.main.IMainModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:24
 */
public class ServerMainGUI extends JFrame implements IMainView {


	public static final int DEFAULT_WEIGTH = 400;
	public static final int DEFAULT_HEIGHT = 300;
	public static final int DEFAULT_X_LOCATION = 500;
	public static final int DEFAULT_Y_LOCATION = 100;
	private final Logger logger;
	private IMainModel model;
	private IMainController mainController;
	private IController controller;

	// 当前群组
	private JList<String> list;
	// 端口号
	private JTextField port;
	// 开启服务器
	private JButton openServer;
	// 关闭服务器
	private JButton closeServer;
	// 用户详细信息
	private JButton groupInformation;
	// 添加群组
	private JButton addGroup;


	public ServerMainGUI(Logger logger) throws HeadlessException {
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
		JPanel settingPanel = new JPanel(new GridLayout(5, 1));
		listPanel.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH / 2.2), DEFAULT_HEIGHT));
		settingPanel.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH / 2.2), DEFAULT_HEIGHT));
		settingPanel.setBorder(BorderFactory.createTitledBorder("setting"));
		listPanel.setBorder(BorderFactory.createTitledBorder("groups"));

		list = new JList<>();
		list.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH / 2.5), (int) (DEFAULT_HEIGHT * 0.75)));

		port = new JTextField();
		port.setPreferredSize(new Dimension(120, 30));
		port.setBorder(BorderFactory.createTitledBorder("port"));
		openServer = new JButton("open server");
		closeServer = new JButton("close server");
		groupInformation = new JButton("group information");
		addGroup = new JButton("add a group");

		listPanel.add(list);
		settingPanel.add(port);
		settingPanel.add(openServer);
		settingPanel.add(closeServer);
		settingPanel.add(groupInformation);
		settingPanel.add(addGroup);

		add(listPanel, BorderLayout.WEST);
		add(settingPanel, BorderLayout.EAST);
	}

	private void addListener() {
		openServer.addActionListener(e -> {
			mainController.openServer();
		});

		closeServer.addActionListener(e -> {
			mainController.closeServer();
		});

		groupInformation.addActionListener(e -> {
			String groupName = list.getSelectedValue();
			if (groupName != null)
				controller.groupInformation(groupName);
		});

		addGroup.addActionListener(e -> {
			mainController.addGroup(controller);
		});


	}

	@Override
	public JTextField getPort() {
		return port;
	}

	@Override
	public JButton getOpenServer() {
		return openServer;
	}

	@Override
	public JButton getCloseServer() {
		return closeServer;
	}

	@Override
	public JButton getAddGroup() { return addGroup; }

	@Override
	public JButton getGroupInformation() {
		return groupInformation;
	}

	@Override
	public void update() {
		Collection<String> allGroups = model.getAllGroups();
		list.setListData(allGroups.toArray(new String[]{}));
	}

	public void setModel(IMainModel model) {
		this.model = model;
		model.rigisterObserver(this);
	}

	public void setMainController(IMainController mainController) {
		this.mainController = mainController;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
}
