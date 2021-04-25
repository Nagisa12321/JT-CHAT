package model.main;

import control.group.ChatGroupServerSocket;
import control.main.ChatMainServerSocket;
import domain.Group;
import observer.Observerable;

import java.util.Collection;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:23
 */
public interface IMainModel extends Observerable {

	void addGroup(Group group);

	void deleteGroup(String group);

	Collection<String> getAllGroups();

	Group getGroupByName(String group);

	boolean containsGroup(String groupName);

	int getPort();

	void setPort(int port);

	boolean isClose();

	void close();

	void start();

	boolean containsUser(String username);

	void addUser(String username, ChatMainServerSocket mainServerSocket);

	void removeUserByName(String username);

	Collection<ChatMainServerSocket> getChatMainServerSockets();

	void addChatMainServerSocket(ChatMainServerSocket chatMainServerSocket);

	boolean containPort(int port);

	void addPort(int port);

	void removePort(int port);
}
