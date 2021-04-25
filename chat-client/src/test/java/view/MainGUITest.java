package view;

import config.ClientConfig;
import control.IController;
import control.main.IMainController;
import model.main.IMainModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.main.IMainView;
import view.main.MainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 0:34
 */
public class MainGUITest {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
		MainGUI gui = context.getBean(MainGUI.class);
		IMainController mainController = context.getBean(IMainController.class);
		IMainModel model = context.getBean(IMainModel.class);
		IController controller = context.getBean(IController.class);

		gui.setMainController(mainController);
		gui.setController(controller);
		gui.setModel(model);

		gui.open();
	}

}
