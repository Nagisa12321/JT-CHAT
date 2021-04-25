package model.main;

import control.group.ChatGroupServerSocket;
import control.main.ChatMainServerSocket;
import domain.Group;
import observer.Observer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:23
 */
public class MainModel implements IMainModel {

	private boolean close;
	private Observer observer;
	private int port;
	private Logger logger;
	private Set<Integer> ports;
	private Map<String, Group> groups;
	private Map<String, ChatMainServerSocket> usersMainSockets;

	public MainModel(Logger logger) {
		this.close = true;
		this.logger = logger;
		usersMainSockets = new HashMap<>();
		groups = new HashMap<>();
		ports = new HashSet<>();
		ports.add(port);
	}

	@Override
	public void rigisterObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObserver() {
		observer.update();
	}

	@Override
	public void addGroup(Group group) {
		groups.put(group.getGroupName(), group);
		addPort(group.getPort());
		notifyObserver();
	}

	@Override
	public void deleteGroup(String group) {
		groups.remove(group);
		notifyObserver();
	}

	@Override
	public Collection<String> getAllGroups() {
		return groups.keySet();
	}


	@Override
	public Group getGroupByName(String group) {
		return groups.getOrDefault(group, null);
	}

	@Override
	public boolean containsGroup(String groupName) {
		return groups.containsKey(groupName);
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
		notifyObserver();
	}


	@Override
	public boolean isClose() {
		return close;
	}

	@Override
	public void close() {
		close = true;
	}

	@Override
	public void start() {
		close = false;
	}

	@Override
	public boolean containsUser(String username) {
		return usersMainSockets.containsKey(username);
	}

	@Override
	public void addUser(String user, ChatMainServerSocket mainServerSocket) {
		usersMainSockets.put(user, mainServerSocket);
		notifyObserver();
	}


	@Override
	public void removeUserByName(String username) {
		usersMainSockets.remove(username);
	}

	@Override
	public Collection<ChatMainServerSocket> getChatMainServerSockets() {
		return usersMainSockets.values();
	}

	@Override
	public void addChatMainServerSocket(ChatMainServerSocket chatMainServerSocket) {
		usersMainSockets.put(chatMainServerSocket.getUser().getUsername(), chatMainServerSocket);
	}

	@Override
	public boolean containPort(int port) {
		return ports.contains(port);
	}

	@Override
	public void addPort(int port) {
		ports.add(port);
	}

	@Override
	public void removePort(int port) {
		ports.remove(port);
	}
}
