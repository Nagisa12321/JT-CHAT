package control;

import domain.Conversation;
import domain.User;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 12:43
 */
public interface IController {

	void connectSuccess();

	void connectFailed();

	void addConversation(Conversation conversation);

	void serverClosed();

	void beenKicked();

	void start();

	void leave();

	void setGroupList(List<String> groupList,String ip, String port);

	void joinTheGroup();

	void joinTheGroup(String groupName, int port);

}
