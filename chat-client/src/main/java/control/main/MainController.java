package control.main;

import domain.User;
import model.main.IMainModel;
import org.apache.log4j.Logger;
import util.JSONParser;
import utils.MessageUtil;
import view.main.IMainView;

import java.io.IOException;
import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 0:13
 */
public class MainController implements IMainController {

	private IMainModel model;
	private IMainView view;

	private Logger logger;

	public MainController(IMainModel model, IMainView view, Logger logger) {
		this.model = model;
		this.view = view;
		this.logger = logger;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MainController{");
		sb.append("model=").append(model);
		sb.append(", view=").append(view);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void createGroup() {

	}

	@Override
	public User joinTheGroup() {
		String groupName = view.getChooseGroupName();
		if (groupName == null) {
			view.openMessage("you have not choose any group");
			return null;
		} else {
			try {
				User user = model.getUser();
				String userAddGroupJSONString =
						JSONParser.createUserAddGroupJSONString(groupName, user.getUsername());
				MessageUtil.sendJSON(userAddGroupJSONString, user.getSocket().getOutputStream());
				return user;
			} catch (IOException e) {
				view.openMessage("add the group failed");
				return null;
			}
		}
	}

	@Override
	public void reflesh() {
		// 发送请求给服务端: 让其回转ip/port/
		try {
			String jsonString = JSONParser.createGetGroupNameListJSONString();
			MessageUtil.sendJSON(jsonString, model.getUser().getSocket().getOutputStream());
		} catch (IOException e) {
			logger.error("[MainController]: " + e);
		}
	}

	@Override
	public void information() {

	}

	@Override
	public void connectSuccess(User user) {
		// 打开主面板并且进行一次刷新操作
		view.open();
		model.setUser(user);
		reflesh();
	}

	@Override
	public void setGroupList(List<String> groupList) {
		model.setGroups(groupList);
	}

	@Override
	public void setIpAndPort(String ip, String port) {
		model.setIP(ip);
		model.setPort(port);
	}

	@Override
	public void serverClosed() {
		view.close();
		view.openMessage("the server closed the connection");
	}

}
