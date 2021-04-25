package control;

import control.group.IGroupController;
import control.main.IMainController;
import domain.Group;
import domain.User;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/20 11:49
 */
public class ServerController implements IController {

	private IGroupController groupController;

	private IMainController mainController;

	private Logger logger;

	public ServerController(IGroupController groupController, IMainController mainController, Logger logger) {
		this.groupController = groupController;
		this.mainController = mainController;
		this.logger = logger;
	}

	@Override
	public void checkUser(User user, IController controller) {
		mainController.checkUser(user, controller);
	}

	@Override
	public void userAddGroup(User user, String groupName, IController controller) {
		groupController.userAddGroup(groupName, user, controller, mainController.userAddGroup(groupName));
	}

	@Override
	public void userAskGroup(User user, String groupName, IController controller) {
		try {
			mainController.userAskGroup(groupName, user);
		} catch (IOException e) {
			logger.info("[ServerController]: send to every user failed...");
		}
	}

	@Override
	public void sendToEveryOne(String groupName, String json) {
		groupController.sendToEveryOne(json, groupName);
	}

	@Override
	public void start() {
		mainController.open();
	}

	@Override
	public void groupInformation(String groupName) {
		groupController.getGroupInformation(groupName);
	}

	@Override
	public void addGroupGUISummit() {
		Group group = mainController.addGroupGUISummit();
		if (group != null) {
			groupController.addGroupGUISummit(group, this);
		}
	}

	@Override
	public void getGroupNameList(User user) {
		mainController.getGroupNameList(user);
	}

	@Override
	public void removeUserByName(String username) {
		mainController.removeUserByName(username);
	}

	@Override
	public void closeAllGroupSockets() {
		groupController.closeServer();
	}

}
