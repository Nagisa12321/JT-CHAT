package domain;

import java.sql.Timestamp;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:33
 */
public class Conversation {

	private String username;
	private String value;
	private Timestamp timestamp;

	public Conversation(String username, String conversation, Timestamp timestamp) {
		this.username = username;
		this.value = conversation;
		this.timestamp = timestamp;
	}

	public Conversation() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Conversation{");
		sb.append("username='").append(username).append('\'');
		sb.append(", conversation='").append(value).append('\'');
		sb.append(", timestamp=").append(timestamp);
		sb.append('}');
		return sb.toString();
	}
}
