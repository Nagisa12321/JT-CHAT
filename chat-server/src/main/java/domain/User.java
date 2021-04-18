package domain;

import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:13
 */
public class User {

	private String username;
	private Socket userSocket;

	public User(String username, Socket userSocket) {
		this.username = username;
		this.userSocket = userSocket;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Socket getUserSocket() {
		return userSocket;
	}

	public void setUserSocket(Socket userSocket) {
		this.userSocket = userSocket;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("username='").append(username).append('\'');
		sb.append(", userSocket=").append(userSocket);
		sb.append('}');
		return sb.toString();
	}
}
