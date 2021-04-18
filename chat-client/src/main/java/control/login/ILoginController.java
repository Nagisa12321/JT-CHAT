package control.login;

import domain.Conversation;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:53
 */
public interface ILoginController {

	void connect();

	void connectSuccess();

	void connectFailed();

	void openView();

}
