package model.chat;

import control.GroupMessageGetter;
import domain.Conversation;
import domain.Group;
import domain.User;
import observer.Observerable;

import java.util.Collection;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:28
 */
public interface IChatModel extends Observerable {


	/**
	 * 是否已经连接上服务器
	 */
	boolean isConnected(String groupName);

	/**
	 * 设置是否连接上服务器
	 */
	void setConnected(String groupName, boolean connected);

	/**
	 * 获取聊天记录
	 */
	List<Conversation> getConversation(String groupName);

	/**
	 * 设置聊天记录
	 */
	void addConversation(String groupName, Conversation conversation);

	void addConversation(String groupName, List<Conversation> conversations);

	User getUser();

	void setUser(User user);

	void addGroup(Group group);

	Group getGroup(String groupName);

	Collection<GroupMessageGetter> getAllGroupGetters();

	Collection<Group> getAllGroups();

	GroupMessageGetter getGroupMessageGetter(String groupName);

	void removeGroupMessageGetter(String groupName);

	void removeAllGroupMessageGetter();

	void addGroupMessageGetter(String groupName, GroupMessageGetter groupMessageGetter);
}
