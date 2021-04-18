package model.login;

import domain.User;
import org.apache.log4j.Logger;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 12:33
 */
public class LoginModel implements ILoginModel{

	private Logger logger;
	private User user;



	public LoginModel(Logger logger) {
		this.logger = logger;
	}

	@Override
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
