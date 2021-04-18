package model;

import domain.User;
import observer.Observer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:18
 */
public class ServerModel implements IModel {

	private Map<String, User> users;
	private Logger logger;

	private Observer observer;

	public ServerModel(Logger logger) {
		this.logger = logger;
		users = new HashMap<>();
	}

	@Override
	public synchronized void addUser(User user) {
		users.put(user.getUsername(), user);
		notifyObserver();
	}

	public Map<String, User> getUsers() {
		return users;
	}

	@Override
	public synchronized void removeAllUser() {
		users.clear();
		notifyObserver();
	}

	@Override
	public synchronized void removeUserByName(String username) {
		users.remove(username);
		notifyObserver();
	}

	@Override
	public synchronized void rigisterObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public synchronized void notifyObserver() {
		observer.update();
	}
}
