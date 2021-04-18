package model.chat;

import domain.Conversation;
import domain.User;
import observer.Observer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:35
 */
public class ChatModel implements IChatModel {

	private final List<Conversation> conversations;
	private User user;
	private Logger logger;
	private boolean connected;
	private Observer observer;


	public ChatModel(Logger logger) {
		this.logger = logger;
		this.connected = false;
		this.conversations = new ArrayList<>();
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	@Override
	public List<Conversation> getConversation() {
		return conversations;
	}

	@Override
	public void addConversation(Conversation conversation) {
		this.conversations.add(conversation);
		notifyObserver();
	}

	@Override
	public void addConversation(List<Conversation> conversations) {
		this.conversations.addAll(conversations);
		notifyObserver();
	}

	@Override
	public User getUser() {
		return user;
	}


	@Override
	public void rigisterObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObserver() {
		observer.update();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
