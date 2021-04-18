package model;

import domain.User;
import observer.Observerable;

import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:14
 */
public interface IModel extends Observerable {

	void addUser(User user);

	void removeUserByName(String username);

	Map<String, User> getUsers();

	void removeAllUser();
}
