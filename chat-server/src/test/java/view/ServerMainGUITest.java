package view;

import config.ServerConfig;
import control.main.IMainController;
import model.main.IMainModel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.main.ServerMainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 14:30
 */
public class ServerMainGUITest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		IMainModel model = context.getBean(IMainModel.class);
		IMainController controller = context.getBean(IMainController.class);
		ServerMainGUI gui = context.getBean(ServerMainGUI.class);

		gui.setModel(model);
		gui.setMainController(controller);


		gui.open();
	}


}
