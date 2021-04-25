package model.chat;

import control.GroupMessageGetter;
import domain.Conversation;
import domain.Group;
import domain.User;
import observer.Observer;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:35
 */
public class ChatModel implements IChatModel {

	private Map<String, Group> group;
	private Map<String, Boolean> connected;
	private Map<String, GroupMessageGetter> groupGetters;
	private User user;
	private Logger logger;
	private Observer observer;


	public ChatModel(Logger logger) {
		this.group = new HashMap<>();
		this.logger = logger;
		this.connected = new HashMap<>();
		this.groupGetters = new HashMap<>();
	}


	@Override
	public boolean isConnected(String groupName) {
		return connected.getOrDefault(groupName, false);
	}

	@Override
	public void setConnected(String groupName, boolean connected) {
		this.connected.put(groupName, connected);
	}

	@Override
	public List<Conversation> getConversation(String groupName) {
		return group.get(groupName).getConversations();
	}

	@Override
	public void addConversation(String groupName, Conversation conversation) {
		group.get(groupName).addConversation(conversation);
		notifyObserver();
	}

	@Override
	public void addConversation(String groupName, List<Conversation> conversations) {
		group.get(groupName).setConversations(conversations);
		notifyObserver();
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void addGroup(Group group) {
		this.group.put(group.getGroupName(), group);
	}

	@Override
	public Group getGroup(String groupName) {
		return group.get(groupName);
	}

	@Override
	public Collection<GroupMessageGetter> getAllGroupGetters() {
		return groupGetters.values();
	}

	@Override
	public Collection<Group> getAllGroups() {
		return group.values();
	}

	@Override
	public GroupMessageGetter getGroupMessageGetter(String groupName) {
		return groupGetters.get(groupName);
	}

	@Override
	public void removeGroupMessageGetter(String groupName) {
		groupGetters.remove(groupName);
	}

	@Override
	public void removeAllGroupMessageGetter() {
		groupGetters.clear();
	}

	@Override
	public void addGroupMessageGetter(String groupName, GroupMessageGetter groupMessageGetter) {
		groupGetters.put(groupName, groupMessageGetter);
	}

	@Override
	public void rigisterObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObserver() {
		observer.update();
	}
}
