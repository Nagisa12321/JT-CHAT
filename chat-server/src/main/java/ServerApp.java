import config.ServerConfig;
import control.IController;
import control.group.ChatGroupServer;
import control.group.IGroupController;
import control.main.ChatMainServer;
import control.main.IMainController;
import model.group.IGroupModel;
import model.main.IMainModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.group.ServerGUI;
import view.main.ServerMainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 20:20
 */
public class ServerApp {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
		/*--controller--*/
		IController controller = context.getBean(IController.class);
		/*-----------------------------group-----------------------------------*/
		/*----------group mvc-------------*/
		IGroupModel groupModel = context.getBean(IGroupModel.class);
		IGroupController groupController = context.getBean(IGroupController.class);
		ServerGUI serverGUI = context.getBean(ServerGUI.class);
		serverGUI.setModel(groupModel);
		serverGUI.setController(groupController);

		/*-----------------------------main-----------------------------------*/

		/*----------main getter------------*/
		ChatMainServer chatMainServer = context.getBean(ChatMainServer.class);
		chatMainServer.setController(controller);
		/*------------main mvc------------*/
		IMainModel mainModel = context.getBean(IMainModel.class);
		IMainController mainController = context.getBean(IMainController.class);
		ServerMainGUI mainGUI = context.getBean(ServerMainGUI.class);
		mainGUI.setMainController(mainController);
		mainGUI.setModel(mainModel);
		mainGUI.setController(controller);
		mainController.setServer(chatMainServer);

		/*--start--*/
		controller.start();
	}
}
