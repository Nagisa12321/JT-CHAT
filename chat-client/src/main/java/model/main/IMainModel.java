package model.main;

import domain.Group;
import domain.User;
import observer.Observerable;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 21:19
 */
public interface IMainModel extends Observerable {

	void setServerIp(String ip);

	String getIP();

	String getPort();

	void setServerPort(String port);

	void setGroups(List<String> groups);

	void setUser(User user);

	User getUser();

	List<String> getGroupList();

	void setIP(String ip);

	void setPort(String port);

}
