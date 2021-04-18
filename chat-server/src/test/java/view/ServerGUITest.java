package view;

import config.ServerConfig;
import control.IController;
import model.IModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerGUITest {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		IModel model = context.getBean(IModel.class);
		IController controller = context.getBean(IController.class);
		ServerGUI view = context.getBean(ServerGUI.class);
		view.setController(controller);
		view.setModel(model);

		view.open();
	}

}