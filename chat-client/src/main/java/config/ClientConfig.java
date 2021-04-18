package config;

import control.ChatMessageGetter;
import control.Controller;
import control.IController;
import control.chat.ChatController;
import control.chat.IChatController;
import control.login.ILoginController;
import control.login.LoginController;
import model.chat.ChatModel;
import model.chat.IChatModel;
import model.login.ILoginModel;
import model.login.LoginModel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import view.chat.ChatGUI;
import view.chat.IChatView;
import view.login.ILoginView;
import view.login.LoginGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:27
 */
@Configuration
public class ClientConfig {

	@Bean
	public Logger logger() {
		return Logger.getLogger(ClientConfig.class);
	}

	@Bean
	public ILoginModel loginModel() {
		return new LoginModel(logger());
	}

	@Bean
	public IChatModel chatModel() { return new ChatModel(logger()); }

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
		return new ChatController(logger(), chatModel(), loginModel(), chatView());
	}

	@Bean
	public IController controller() {
		return new Controller(chatController(), loginController(), logger());
	}

	@Bean
	public IChatView chatView() {
		return new ChatGUI(logger());
	}

	@Bean
	public ILoginView loginView() {
		return new LoginGUI(logger());
	}
}



