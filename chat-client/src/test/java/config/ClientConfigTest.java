package config;

import control.chat.IChatController;
import model.chat.IChatModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.chat.ChatGUI;

public class ClientConfigTest {
	@Test
	public void testGetMVC() {

		ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
		IChatModel model = context.getBean(IChatModel.class);
		IChatController controller = context.getBean(IChatController.class);
		ChatGUI view = context.getBean(ChatGUI.class);
		view.setChatController(controller);
		view.setModel(model);

		System.out.println(model);
		System.out.println(controller);
		System.out.println(view);
	}

}