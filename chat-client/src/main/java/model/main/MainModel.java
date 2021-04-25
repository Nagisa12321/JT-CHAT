package model.main;

import domain.Group;
import domain.User;
import observer.Observer;
import observer.Observerable;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 0:13
 */
public class MainModel implements IMainModel {
	private User user;
	private Logger logger;
	private String serverIp;
	private String serverPort;
	private List<String> groups;
	private Observer observer; // view

	public MainModel(Logger logger) {
		this.groups = new ArrayList<>();
		this.logger = logger;
		this.serverIp = "";
		this.serverPort = "";
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Override
	public String getIP() {
		return serverIp;
	}

	@Override
	public String getPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
		notifyObserver();
	}

	@Override
	public void setUser(User user) {
		this.user = user;
		notifyObserver();
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public List<String> getGroupList() {
		return groups;
	}

	@Override
	public void setIP(String ip) {
		this.serverIp = ip;
		notifyObserver();
	}

	@Override
	public void setPort(String port) {
		this.serverPort = port;
		notifyObserver();
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
