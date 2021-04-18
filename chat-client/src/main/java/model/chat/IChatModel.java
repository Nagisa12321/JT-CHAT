package model.chat;

import domain.Conversation;
import domain.User;
import observer.Observerable;

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
	boolean isConnected();

	/**
	 * 设置是否连接上服务器
	 */
	void setConnected(boolean connected);

	/**
	 * 获取聊天记录
	 */
	List<Conversation> getConversation();

	/**
	 * 设置聊天记录
	 */
	void addConversation(Conversation conversation);

	void addConversation(List<Conversation> conversations);

	User getUser();

	void setUser(User user);
}
