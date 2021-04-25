package control.main;

import control.IController;
import domain.Group;
import domain.User;

import java.io.IOException;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:24
 */
public interface IMainController {

	void closeServer();

	void openServer();

	void addGroup(IController controller);

	void addGroup(Group group);

	void checkUser(User user, IController controller);

	void removeUserByName(String username);

	void getGroupNameList(User user);

	Group addGroupGUISummit();

	void setServer(ChatMainServer server);

	void open();

	void userAskGroup(String groupName, User user) throws IOException;

	int userAddGroup(String groupName) ;
}
