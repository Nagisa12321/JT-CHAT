package model.login;

import domain.User;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:57
 */
public interface ILoginModel {

	User getUser();

	void setUser(User user);

}
