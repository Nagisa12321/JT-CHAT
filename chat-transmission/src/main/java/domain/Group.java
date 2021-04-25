package domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 0:06
 */
public class Group {

	private int port;
	private String groupName;
	private String creator;
	private Date createTime;
	private List<Conversation> conversations;

	public Group(int port, String groupName, String creator, Date createTime, List<Conversation> conversations) {
		this.port = port;
		this.groupName = groupName;
		this.creator = creator;
		this.createTime = createTime;
		this.conversations = conversations;
	}

	public Group() {
		conversations = new ArrayList<>();
	}



	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations.addAll(conversations);
	}

	public void addConversation(Conversation conversation){
		this.conversations.add(conversation);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Group{");
		sb.append("port=").append(port);
		sb.append(", groupName='").append(groupName).append('\'');
		sb.append(", creator='").append(creator).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", conversations=").append(conversations);
		sb.append('}');
		return sb.toString();
	}
}
