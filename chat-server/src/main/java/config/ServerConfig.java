package config;

import control.IController;
import control.ServerController;
import model.IModel;
import model.ServerModel;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import view.IView;
import view.ServerGUI;

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

	@Bean
	public IModel model() {
		return new ServerModel(logger());
	}

	@Bean
	public IController controller() {
		return new ServerController(logger(), model(), view());
	}

	@Bean
	public IView view() {
		return new ServerGUI(logger());
	}

}
