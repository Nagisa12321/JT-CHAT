package config;

import control.IController;
import control.ServerController;
import control.group.ChatGroupServer;
import control.group.IGroupController;
import control.group.ServerGroupController;
import control.main.ChatMainServer;
import control.main.IMainController;
import control.main.MainController;
import model.group.IGroupModel;
import model.group.ServerGroupModel;
import model.main.IMainModel;
import model.main.MainModel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import view.group.IGroupView;
import view.group.ServerGUI;
import view.main.IMainView;
import view.main.ServerMainGUI;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:25
 */
@Configuration
public class ServerConfig {

	@Bean
	public Logger logger() {
		return Logger.getLogger(ServerConfig.class);
	}

	/*-------------model--------------*/

	@Bean
	public IGroupModel groupModel() {
		return new ServerGroupModel(logger());
	}

	@Bean
	public IMainModel mainModel() { return new MainModel(logger()); }

	/*-------------controller--------------*/

	@Bean
	public IController controller() { return new ServerController(groupController(), mainController(), logger()); }

	@Bean
	public ChatMainServer chatMainServer() {
		return new ChatMainServer(logger());
	}


	@Bean
	public IGroupController groupController() {
		return new ServerGroupController(logger(), groupModel(), groupView());
	}

	@Bean
	public IMainController mainController() {
		return new MainController(mainModel(), mainView(), logger(), chatMainServer());
	}

	/*--------------view------------------*/
	@Bean
	public IGroupView groupView() {
		return new ServerGUI(logger());
	}

	@Bean
	public IMainView mainView() {
		return new ServerMainGUI(logger());
	}

}
