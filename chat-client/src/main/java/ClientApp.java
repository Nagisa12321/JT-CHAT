import config.ClientConfig;
import control.ChatMessageGetter;
import control.IController;
import control.chat.IChatController;
import control.login.ILoginController;
import control.main.IMainController;
import model.chat.IChatModel;
import model.main.IMainModel;
import model.main.MainModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import view.chat.ChatGUI;
import view.login.LoginGUI;
import view.main.MainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 20:19
 */
public class ClientApp {
	public static void main(String[] args) {
		ChatGUI chatGui;
		ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);

		// chat servlet
		IController controller = context.getBean(IController.class);
		ChatMessageGetter chatMessageGetter = context.getBean(ChatMessageGetter.class);
		chatMessageGetter.setController(controller);

		// chat chatGui
		/*
		IChatModel chatModel = context.getBean(IChatModel.class);
		IChatController chatController = context.getBean(IChatController.class);
		chatGui = context.getBean(ChatGUI.class);
		chatGui.setChatController(chatController);
		chatGui.setModel(chatModel);
		chatGui.setController(controller);
		*/


		// login chatGui
		LoginGUI loginGUI = context.getBean(LoginGUI.class);
		ILoginController loginController = context.getBean(ILoginController.class);
		loginGUI.setLoginController(loginController);
		loginGUI.setController(controller);

		// main chatGui
		MainGUI mainGUI = context.getBean(MainGUI.class);
		IMainController mainController = context.getBean(IMainController.class);
		IMainModel mainModel = context.getBean(IMainModel.class);
		mainGUI.setMainController(mainController);
		mainGUI.setModel(mainModel);
		mainGUI.setController(controller);

		controller.start();
	}
}
