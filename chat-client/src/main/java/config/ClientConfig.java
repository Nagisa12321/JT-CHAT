package config;

import control.ChatMessageGetter;
import control.Controller;
import control.IController;
import control.chat.ChatController;
import control.chat.IChatController;
import control.login.ILoginController;
import control.login.LoginController;
import control.main.IMainController;
import control.main.MainController;
import model.chat.ChatModel;
import model.chat.IChatModel;
import model.login.ILoginModel;
import model.login.LoginModel;
import model.main.IMainModel;
import model.main.MainModel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import view.chat.ChatGUI;
import view.chat.IChatView;
import view.login.ILoginView;
import view.login.LoginGUI;
import view.main.IMainView;
import view.main.MainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:27
 */
@Configuration
public class ClientConfig {
	/*-------------------logger----------------------*/

	@Bean
	public Logger logger() {
		return Logger.getLogger(ClientConfig.class);
	}

	/*-------------------model----------------------*/

	@Bean
	public ILoginModel loginModel() {
		return new LoginModel(logger());
	}

	@Bean
	public IChatModel chatModel() { return new ChatModel(logger()); }

	@Bean
	public IMainModel mainModel() { return new MainModel(logger()); }

	/*-------------------controller----------------------*/

	@Bean
	public ChatMessageGetter getter() {
		return new ChatMessageGetter(logger());
	}

	@Bean
	public ILoginController loginController() {
		return new LoginController(loginView(), loginModel(), logger(), getter());
	}

	@Bean
	public IChatController chatController() {
		return new ChatController(logger(), chatModel(), loginModel());
	}

	@Bean
	public IController controller() {
		return new Controller(mainController(), chatController(), loginController(), logger());
	}

	@Bean
	public IMainController mainController() { return new MainController(mainModel(), mainView(), logger()); }

	/*-------------------view----------------------*/

	@Bean
	public ILoginView loginView() {
		return new LoginGUI(logger());
	}

	@Bean
	public IMainView mainView() { return new MainGUI(logger()); }


}



