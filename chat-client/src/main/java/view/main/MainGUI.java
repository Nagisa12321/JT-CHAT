package view.main;

import control.IController;
import control.main.IMainController;
import model.main.IMainModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 21:21
 */
public class MainGUI extends JFrame implements IMainView {
	public static final int DEFAULT_WEIGTH = 300;
	public static final int DEFAULT_HEIGHT = 800;
	public static final int DEFAULT_X_LOCATION = 1150;
	public static final int DEFAULT_Y_LOCATION = 10;

	private IMainModel model;
	private IMainController mainController;
	private IController controller;
	private Logger logger;

	private JButton createGroup;
	private JButton joinTheGroup;
	private JButton reflesh;
	private JButton information;
	private JLabel username;
	private JLabel ip;
	private JLabel port;
	private JList<String> groups;

	public MainGUI(Logger logger) throws HeadlessException {
		this.logger = logger;

		defaultSetting();
		addMainPanel();
		addListener();
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

	private void addListener() {
		reflesh.addActionListener(e -> {
			mainController.reflesh();
		});

		joinTheGroup.addActionListener(e -> {
			controller.joinTheGroup();
		});
	}

	@Override
	public void update() {
		username.setText(model.getUser().getUsername());
		ip.setText(model.getIP());
		port.setText(model.getPort());
		groups.setListData(model.getGroupList().toArray(new String[]{}));
	}

	@Override
	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void open() {
		setVisible(true);
	}

	@Override
	public void close() {
		setVisible(false);
	}

	// 默认设置
	private void defaultSetting() {

		// 设置图标
		ImageIcon imageIcon = new ImageIcon("src/main/resources/img/pic.jpg");
		setIconImage(imageIcon.getImage());

		// 设置标题
		setTitle("--JT-CHAT-Client--                - by jtchen");
		// 设置方位
		setLocation(DEFAULT_X_LOCATION, DEFAULT_Y_LOCATION);
		// 设置不可变大小
		setResizable(false);
		// 设置默认长宽
		setSize(DEFAULT_WEIGTH, DEFAULT_HEIGHT);
		// 设置关闭动作
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addMainPanel() {
		JPanel information = new JPanel(new GridLayout(3, 1));
		JPanel setting = new JPanel(new GridLayout(4, 1));
		JPanel list = new JPanel();

		information.setBorder(BorderFactory.createTitledBorder("information"));
		setting.setBorder(BorderFactory.createTitledBorder("setting"));
		list.setBorder(BorderFactory.createTitledBorder("groups"));

		information.setPreferredSize(new Dimension(DEFAULT_WEIGTH, (int) (DEFAULT_HEIGHT * .25)));
		setting.setPreferredSize(new Dimension(DEFAULT_WEIGTH, (int) (DEFAULT_HEIGHT * .15)));


		/*---------------information----------------*/

		username = new JLabel();
		ip = new JLabel();
		port = new JLabel();

		username.setHorizontalAlignment(JLabel.CENTER);
		ip.setHorizontalAlignment(JLabel.CENTER);
		port.setHorizontalAlignment(JLabel.CENTER);

		username.setBorder(BorderFactory.createTitledBorder("username"));
		ip.setBorder(BorderFactory.createTitledBorder("ip"));
		port.setBorder(BorderFactory.createTitledBorder("port"));

		username.setForeground(Color.red);
		information.add(username);
		information.add(port);
		information.add(ip);

		/*----------------setting----------------------*/
		createGroup = new JButton("create group");
		joinTheGroup = new JButton("join the group");
		reflesh = new JButton("reflesh");
		this.information = new JButton("information");
		setting.add(createGroup);
		setting.add(joinTheGroup);
		setting.add(reflesh);
		setting.add(this.information);

		/*---------------group list-------------------*/
		this.groups = new JList<>();
		this.groups.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH * 0.9), (int) (DEFAULT_HEIGHT * 0.6)));
		groups.setFixedCellHeight(60);
		groups.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		list.add(groups);


		add(information, BorderLayout.NORTH);
		add(list);
		add(setting, BorderLayout.SOUTH);
	}

	@Override
	public String getChooseGroupName() {
		return groups.getSelectedValue();
	}
}
