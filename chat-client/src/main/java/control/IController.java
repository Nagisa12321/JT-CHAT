package control;

import domain.Conversation;
import domain.User;

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

}
