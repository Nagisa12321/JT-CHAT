package view;

import config.ServerConfig;
import control.group.IGroupController;
import model.group.IGroupModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.group.ServerGUI;

public class ServerGUITest {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		IGroupModel model = context.getBean(IGroupModel.class);
		IGroupController controller = context.getBean(IGroupController.class);
		ServerGUI view = context.getBean(ServerGUI.class);
		view.setController(controller);
		view.setModel(model);

		view.open();
	}

}