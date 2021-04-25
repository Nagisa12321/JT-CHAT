package control.login;

import domain.Conversation;
import domain.User;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:53
 */
public interface ILoginController {

	void connect();

	User connectSuccess();

	void connectFailed();

	void openView();

}
