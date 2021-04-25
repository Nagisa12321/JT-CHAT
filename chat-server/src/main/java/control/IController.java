package control;

import domain.User;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/20 11:49
 */
public interface IController {

	void getGroupNameList(User user);

	void removeUserByName(String username);

	void closeAllGroupSockets();

	void checkUser(User user, IController controller);

	void userAddGroup(User user, String groupName, IController controller);

	void userAskGroup(User user, String groupName, IController controller);

	void sendToEveryOne(String groupName, String json) ;

	void start();

	void groupInformation(String groupName);

	void addGroupGUISummit();
}
