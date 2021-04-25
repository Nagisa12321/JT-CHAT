package domain;

import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 12:24
 */
public class User {

	private String username;
	private Socket socket;

	public User(String username, Socket socket) {
		this.username = username;
		this.socket = socket;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("username='").append(username).append('\'');
		sb.append(", socket=").append(socket);
		sb.append('}');
		return sb.toString();
	}
}
