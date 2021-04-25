package control.chat;

import control.IController;
import domain.Conversation;
import domain.User;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:28
 */
public interface IChatController {

	void send();

	void setUser(User user);

	void serverClosed();

	void addConversation(Conversation conversation);

	void leave();

	void beenKicked();

	void connectSuccess();

	void newGUI(String groupName, IController controller);

	void joinTheGroup(String groupName, int port, IController controller);
}
