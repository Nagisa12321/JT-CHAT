package view.chat;

import control.IController;
import control.chat.IChatController;
import domain.Conversation;
import model.chat.IChatModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:26
 */
public class ChatGUI extends JFrame implements IChatView {


	public static final int DEFAULT_WEIGTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int DEFAULT_X_LOCATION = 250;
	public static final int DEFAULT_Y_LOCATION = 100;
	private final Logger logger;
	private IController controller;
	private IChatController chatController;
	private IChatModel model;
	// 聊天记录
	private JTextArea area;
	// 离开服务器
	private JButton leave;
	// 发送消息
	private JButton send;
	// 消息框
	private JTextArea messageArea;

	public ChatGUI(Logger logger) throws HeadlessException {
		this.logger = logger;
		defaultSetting();
		addChatPanel();
		addListener();
	}


	@Override
	public JTextArea getArea() {
		return area;
	}

	@Override
	public JButton getLeave() {
		return leave;
	}

	@Override
	public JButton getSend() {
		return send;
	}

	@Override
	public JTextArea getMessageArea() {
		return messageArea;
	}


	@Override
	public void openMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "!!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
	}


	@Override
	public void closeConnect() {
		setVisible(false);
	}

	@Override
	public void open() {
		setVisible(true);
	}

	private void addListener() {
		send.addActionListener(e -> chatController.send());
		leave.addActionListener(e -> controller.leave());
	}

	private void addChatPanel() {
		JPanel messagePanel = new JPanel();
		JPanel editPanel = new JPanel();
		JPanel sendPanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(DEFAULT_WEIGTH, (int) (DEFAULT_HEIGHT * 0.6)));
		editPanel.setPreferredSize(new Dimension(DEFAULT_WEIGTH, (int) (DEFAULT_HEIGHT * 0.3)));
		sendPanel.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH * 0.8), (int) (DEFAULT_HEIGHT * 0.1)));
		sendPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		area = new JTextArea();
		area.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		area.setLineWrap(true);
		area.setEditable(false);
		JScrollPane jsp = new JScrollPane(area);
		jsp.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH * 0.8), (int) (DEFAULT_HEIGHT * 0.5)));
		DefaultCaret caret = (DefaultCaret) area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		leave = new JButton("leave");
		send = new JButton("send");

		messageArea = new JTextArea();
		messageArea.setPreferredSize(new Dimension((int) (DEFAULT_WEIGTH * 0.8), (int) (DEFAULT_HEIGHT * 0.3)));

		messagePanel.add(jsp);

		editPanel.add(messageArea);
		sendPanel.add(send);
		sendPanel.add(leave);

		add(messagePanel, BorderLayout.NORTH);
		add(editPanel);
		add(sendPanel, BorderLayout.SOUTH);

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

	public void setChatController(IChatController chatController) {
		this.chatController = chatController;
	}

	public void setModel(IChatModel model) {
		this.model = model;
		model.rigisterObserver(this);
	}

	@Override
	public void update() {
		logger.info("[view]: get the message from model");
		List<Conversation> conversation = model.getConversation();
		paintConversation(conversation);
	}

	private void paintConversation(List<Conversation> conversations) {
		StringBuilder builder = new StringBuilder();
		for (Conversation conversation : conversations) {
			String username = conversation.getUsername();
			String val = conversation.getValue();
			Timestamp timestamp = conversation.getTimestamp();
			builder.append(username).append(" [").append(timestamp).append("] \n - ").append(val).append('\n');
		}
		area.setText(builder.toString());
	}


	public IController getController() {
		return controller;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
}
