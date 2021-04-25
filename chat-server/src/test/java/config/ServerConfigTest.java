package config;

import control.group.IGroupController;
import model.group.IGroupModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.group.ServerGUI;

public class ServerConfigTest {

	@Test
	public void testGetMVC() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		IGroupModel model = context.getBean(IGroupModel.class);
		IGroupController controller = context.getBean(IGroupController.class);
		ServerGUI view = context.getBean(ServerGUI.class);
		view.setController(controller);
		view.setModel(model);

		System.out.println(model);
		System.out.println(view);
		System.out.println(controller);
	}

}