package model.group;

import control.group.ChatGroupServer;
import control.group.ChatGroupServerSocket;
import domain.User;
import observer.Observerable;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:14
 */
public interface IGroupModel extends Observerable {

	Map<String, ChatGroupServerSocket> getGroupServerSockets(String groupName);

	void addGroupServerSocket(String groupName, ChatGroupServerSocket chatGroupServerSocket);

	List<String> getGroupNameList();

	void removeAllGroupServerSockets();

	ChatGroupServer getGroupServer(String groupName);

	void addGroupServer(String groupName, ChatGroupServer groupServer);

	void removeAllGroupServers();

}
