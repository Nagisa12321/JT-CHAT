package view;

import config.ClientConfig;
import control.chat.IChatController;
import model.chat.IChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.chat.ChatGUI;

public class ChatGUITest {

	public static void main(String[] args) {
		ChatGUI gui;
		ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
		IChatModel model = context.getBean(IChatModel.class);
		IChatController controller = context.getBean(IChatController.class);
		gui = context.getBean(ChatGUI.class);
		gui.setChatController(controller);
		gui.setModel(model);

		gui.open();

	}
}