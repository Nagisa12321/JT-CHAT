package model.group;

import control.group.ChatGroupServer;
import control.group.ChatGroupServerSocket;
import observer.Observer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:18
 */
public class ServerGroupModel implements IGroupModel {

	private Map<String, Map<String, ChatGroupServerSocket>> groupServerSockets;
	private Map<String, ChatGroupServer> groupServers;
	private Logger logger;

	private Observer observer;

	public ServerGroupModel(Logger logger) {
		this.logger = logger;
		groupServerSockets = new HashMap<>();
		groupServers = new HashMap<>();
	}

//	@Override
//	public synchronized void addUser(String groupName, User user) {
//		Map<String, User> userMap = users.get(groupName);
//		if (userMap == null) {
//			HashMap<String, User> tmpUserMap = new HashMap<>();
//			tmpUserMap.put(user.getUsername(), user);
//			users.put(groupName, tmpUserMap);
//		} else {
//			userMap.put(user.getUsername(), user);
//		}
//
//		notifyObserver();
//	}

//	public Map<String, User> getUsers(String groupName) {
//		return users.get(groupName);
//	}

//	@Override
//	public synchronized void removeAllUser() {
//		users.clear();
//		notifyObserver();
//	}

	@Override
	public Map<String, ChatGroupServerSocket> getGroupServerSockets(String groupName) {
		return groupServerSockets.get(groupName);
	}

	@Override
	public void addGroupServerSocket(String groupName, ChatGroupServerSocket chatGroupServerSocket) {
		groupServerSockets.get(groupName).put(chatGroupServerSocket.getUser().getUsername(), chatGroupServerSocket);
		notifyObserver();
	}

	@Override
	public List<String> getGroupNameList() {
		return new ArrayList<>(groupServerSockets.keySet());
	}

	@Override
	public void removeAllGroupServerSockets() {
		groupServerSockets.clear();
		notifyObserver();
	}

	@Override
	public ChatGroupServer getGroupServer(String groupName) {
		return groupServers.get(groupName);
	}

	@Override
	public void addGroupServer(String groupName, ChatGroupServer groupServer) {
		groupServerSockets.put(groupName, new HashMap<>());
		groupServers.put(groupName, groupServer);
	}

	@Override
	public void removeAllGroupServers() {
		groupServers.clear();
	}

//	@Override
//	public synchronized void removeUserByName(String username) {
//		users.remove(username);
//		notifyObserver();
//	}

	@Override
	public synchronized void rigisterObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public synchronized void notifyObserver() {
		observer.update();
	}
}
