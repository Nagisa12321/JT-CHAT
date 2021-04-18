package control;

import domain.User;

import java.io.OutputStream;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:17
 */
public interface IController {

	void openServer();

	void closeServer();

	void addUser(User user);

	void sendToEveryOne(String json);

	void sendToUserByName(String json, String username);

	void removeUserByName(String username);

	void removeAllUser();

	void kickUserByName(String username);

	void getUserInformation(String username);

	boolean checkUser(String username, OutputStream os);
}
