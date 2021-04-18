import config.ServerConfig;
import control.IController;
import model.IModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.ServerGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 20:20
 */
public class ServerApp {
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
