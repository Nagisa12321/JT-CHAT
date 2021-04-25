package util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.util.List;

import static util.JSONType.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 16:12
 */
public class JSONParser {

	public static String createUserInformationJSONString(String username) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_USER_INFORMATION);
		json.put(USER_NAME, username);

		return json.toJSONString();
	}

	public static String createUserLeaveJSONString() {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_LEAVE);
		return json.toJSONString();
	}

	public static String createUserIntroductionJSONString(String username, String message) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_MESSAGE);
		json.put(USER_NAME, username);
		json.put(MESSAGE, message);

		return json.toJSONString();
	}

	public static String createMessageJSONString(String username, String message) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_MESSAGE);
		json.put(USER_NAME, username);
		json.put(VALUE, message);
		json.put(TIME, new Timestamp(System.currentTimeMillis()).toString());

		return json.toJSONString();
	}

	public static String createServerOverJSONString() {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_SERVER_BEEN_SHUTDOWN);
		return json.toJSONString();
	}

	public static String createSystemMessageJSONString(String message) {
		return createMessageJSONString("=== GM ===", message);
	}

	public static String createGMKickYouJSONString() {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_KICK);
		return json.toJSONString();
	}

	public static String createCheckUserJSONString(String username) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_CHECK_USERS);
		json.put(USER_NAME, username);

		return json.toJSONString();
	}

	public static String createCheckUserJSONString(boolean exist) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_CHECK_USERS);
		json.put(EXIST, exist);

		return json.toJSONString();
	}

	public static String createGetGroupNameListJSONString() {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_GET_GROUP_NAMELIST);
		return json.toJSONString();
	}

	public static String createGetGroupNameListJSONString(List<String> nameList, String ip, String port) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_GET_GROUP_NAMELIST);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(nameList);
		json.put(NAME_LIST, jsonArray);
		json.put(IP, ip);
		json.put(PORT, port);

		return json.toJSONString();
	}

	public static String createUserAddGroupJSONString(String groupName, String userName) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_USER_ADD_GROUP);
		json.put(USER_NAME, userName);
		json.put(GROUP_NAME, groupName);

		return json.toJSONString();
	}

	public static String createReplyGroupPort(int port, String groupName) {
		JSONObject json = new JSONObject();
		json.put(TYPE, TYPE_GROUP_PORT);
		json.put(PORT, port);
		json.put(GROUP_NAME, groupName);

		return json.toJSONString();
	}
}
