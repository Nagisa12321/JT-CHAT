package config;

import control.IController;
import model.IModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.ServerGUI;

public class ServerConfigTest {

	@Test
	public void testGetMVC() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		IModel model = context.getBean(IModel.class);
		IController controller = context.getBean(IController.class);
		ServerGUI view = context.getBean(ServerGUI.class);
		view.setController(controller);
		view.setModel(model);

		System.out.println(model);
		System.out.println(view);
		System.out.println(controller);
	}

}