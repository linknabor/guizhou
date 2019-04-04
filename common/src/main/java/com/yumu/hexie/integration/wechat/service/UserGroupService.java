package com.yumu.hexie.integration.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.user.UserGroup;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;

/**
 * 分组管理
 */
public class UserGroupService {

	private static final Logger log = LoggerFactory.getLogger(UserGroupService.class);

	/**
	 * 创建分组URL
	 */
	private static String CREATE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";

	/**
	 * 查询所有分组
	 */
	private static String GET_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";

	/**
	 * 查询用户所在分组
	 */
	private static String GET_USER_GOUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";

	/**
	 * 修改分组名
	 */

	private static String UPDATE_GROUP_NAME = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";

	/**
	 * 移动用户分组
	 */

	private static String MOVE_USER_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

	/**
	 * 创建分组
	 * 
	 * @param groupName
	 *            分组名称
	 * @return 分组ID
	 */
	public static String createGroup(String groupName, String accessToekn) {

		String id = null;

		String data = "{\"group\":{\"name\":\"" + groupName + "\"}}";

		WechatResponse jsonObject = WeixinUtil.httpsRequest(CREATE_GROUP_URL, "POST", data, accessToekn);

		if (null != jsonObject&&jsonObject.getErrcode() == 0
				&&jsonObject.getGroup()!=null) {
			id = jsonObject.getGroup().getId();
		} else {
			if(jsonObject!=null)
			log.error("创建分组失败，errcode:" + jsonObject.getErrcode()
					+ "，errmsg:" + jsonObject.getErrmsg());
		}
		return id;
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 分组列表
	 */
	public static List<UserGroup> getGroup(String accessToekn) {

		List<UserGroup> list = new ArrayList<UserGroup>();
		WechatResponse jsonObject = WeixinUtil.httpsRequest(GET_GROUP_URL, "POST", null, accessToekn);
		if (null != jsonObject && jsonObject.getErrcode() == 0
				&& jsonObject.getGroups() != null) {
			for (UserGroup group : jsonObject.getGroups()) {
				list.add(group);
			}
		} else {
			if(jsonObject!=null)
				log.error("获取分组失败，errcode:" + jsonObject.getErrcode() + "，errmsg:"
						+ jsonObject.getErrmsg());
		}
		return list;
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openid
	 *            用户openid
	 * @return 分组id
	 */
	public static String getGroupByOpenid(String openid, String accessToken) {
		String data = "{\"openid\":\"" + openid + "\"}";
		WechatResponse jsonObject = WeixinUtil.httpsRequest(GET_USER_GOUP, "POST", data, accessToken);

		if (null != jsonObject && jsonObject.getErrcode() == 0) {
			return jsonObject.getGroupid();
			} else {
				if(jsonObject!=null)
					log.error("获取分组失败，errcode:" + jsonObject.getErrcode() + "，errmsg:"
							+ jsonObject.getErrmsg());
				return null;
		}
		
	}

	/**
	 * 修改分组名称
	 * 
	 * @param id
	 *            分组id
	 * @param name
	 *            分组name
	 * @return 是否成功
	 */
	public static boolean updateGroupName(int id, String name, String accessToken) {


		String data = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name
				+ "\"}}";

		WechatResponse jsonObject = WeixinUtil.httpsRequest(UPDATE_GROUP_NAME, "POST", data, accessToken);

		return (null != jsonObject && jsonObject.getErrcode() == 0);
	}

	/**
	 * 移动用户分组
	 * @param openid	用户openid
	 * @param groupId	分组id
	 * @return	是否成功
	 */
	public static boolean moveUserToGroup(String openid, int groupId, String accessToken) {
		String data = "{\"openid\":\"" + openid + "\",\"to_groupid\":"
				+ groupId + "}";
		WechatResponse jsonObject = WeixinUtil.httpsRequest(MOVE_USER_TO_GROUP, "POST", data, accessToken);
		return (null != jsonObject && jsonObject.getErrcode() == 0);
	}
}
