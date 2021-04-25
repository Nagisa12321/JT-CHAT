package control.group;

import control.IController;
import domain.Group;
import domain.User;

import java.io.OutputStream;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:17
 */
public interface IGroupController {

//	void openServer();

	void closeServer();

	void sendToEveryOne(String json, String groupName);

	void sendToUserByName(String json, String groupName, String username);

//	void removeUserByName(String username);

	void removeAllUser();

	void kickUserByName(String groupName, String username);

	void getUserInformation(String groupName, String username);

//	boolean checkUser(String username, OutputStream os);

	void userAddGroup(String groupName, User user, IController controller, int port);

	void shutDownGroupSocket(String groupName, String username);

	void getGroupInformation(String groupName);

	void openServer(String groupName, int port, IController controller);

	void addGroupGUISummit(Group group, IController controller);
}
