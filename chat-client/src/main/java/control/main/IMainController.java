package control.main;

import domain.User;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 21:20
 */
public interface IMainController {

	void createGroup();

	User joinTheGroup();

	void reflesh();

	void information();

	void connectSuccess(User user);

	void setGroupList(List<String> groupList);

	void setIpAndPort(String ip, String port);

	void serverClosed();

}
