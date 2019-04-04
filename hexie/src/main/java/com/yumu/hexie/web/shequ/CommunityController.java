package com.yumu.hexie.web.shequ;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.yumu.hexie.common.Constants;
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.qiniu.util.QiniuUtil;
import com.yumu.hexie.integration.wechat.service.FileService;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.community.Annoucement;
import com.yumu.hexie.model.community.CommunityInfo;
import com.yumu.hexie.model.community.Thread;
import com.yumu.hexie.model.community.ThreadComment;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.shequ.CommunityService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "communityController")
public class CommunityController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CommunityController.class);
	
	private static final int PAGE_SIZE = 10;
	
    @Value(value = "${tmpfile.dir}")
    private String tmpFileRoot;
    
    @Value(value = "${qiniu.domain}")
    private String domain;

	
	@Inject
	private CommunityService communityService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private SystemConfigService systemConfigService;
	
	/*****************[BEGIN]帖子********************/
	
	/**
	 * 首页获取帖子列表
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thread/getThreadList/{filter}/{currPage}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<List<Thread>> getThreadList(@ModelAttribute(Constants.USER)User user, @RequestBody Thread thread, 
				@PathVariable String filter,  @PathVariable int currPage ) throws Exception {
		
		Sort sort = new Sort(Direction.DESC , "stickPriority", "createDate", "createTime");
		user = userService.getById(user.getId());
		List<Thread>list = new ArrayList<Thread>();
		Pageable page = new PageRequest(currPage, PAGE_SIZE, sort);
		list = communityService.getThreadListByUserId(user.getId(), thread.getThreadCategory(), page);
			
		for (int i = 0; i < list.size(); i++) {
			
			Thread td = list.get(i);
			String attachmentUrl = td.getAttachmentUrl();
//			if (StringUtil.isEmpty(attachmentUrl)) {
//				moveImgsFromTencent2Qiniu(td);
//			}
			
			if (!StringUtil.isEmpty(attachmentUrl)) {
				
				String[]urls = attachmentUrl.split(",");
				
				List<String>imgLinkList = new ArrayList<String>();
				List<String>previewLinkList = new ArrayList<String>();
				
				for (int j = 0; j < (urls.length>3?3:urls.length); j++) {
					
					String urlKey = urls[j];
					imgLinkList.add(QiniuUtil.getInstance().getInterlaceImgLink(urlKey, "1"));
					previewLinkList.add(QiniuUtil.getInstance().getPreviewLink(urlKey, "1", "0"));
					
				}
				
				td.setImgUrlLink(imgLinkList);
				td.setPreviewLink(previewLinkList);
			}
			
		}
		
		for (int i = 0; i < list.size(); i++) {
			
			Thread td = list.get(i);
			String attachmentUrl = td.getAttachmentUrl();
			if (!StringUtil.isEmpty(attachmentUrl)) {
				
				String[]urls = attachmentUrl.split(",");
				
				List<String>thumbnailLinkList = new ArrayList<String>();
				
				for (int j = 0; j < (urls.length>3?3:urls.length); j++) {
					
					String urlKey = urls[j];
					thumbnailLinkList.add(QiniuUtil.getInstance().getThumbnailLink(urlKey, "3", "0"));
					
				}
				
				td.setThumbnailLink(thumbnailLinkList);
			}
			
		}
		
		
		log.debug("list is : " + list);
		List<Object> totle_list = new ArrayList<Object>();
		totle_list.add(list);
		totle_list.add(user.getSect_id());
		return BaseResult.successResult(totle_list);
		
	}
	
	/**
	 * 新增帖子
	 * @param session
	 * @param thread
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thread/addThread", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> addThread(HttpSession session, @RequestBody Thread thread) throws Exception{
		
		User user = (User)session.getAttribute(Constants.USER);
		
		if(thread.getThreadContent().length()>200){
			
			return BaseResult.fail("发布信息内容超过200字。");
		}
		
		communityService.addThread(user, thread);
		
		moveImgsFromTencent2Qiniu(thread);	//更新图片的路径
		
		return BaseResult.successResult("信息发布成功。");
		
		
	}
	
	/**
	 * 删除帖子
	 * @param session
	 * @param threadId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/thread/deleteThread", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<?> deleteThread(HttpSession session, @RequestBody Thread thread) throws Exception{
		
		User user = (User)session.getAttribute(Constants.USER);
		
		if (StringUtil.isEmpty(thread.getThreadId())) {
			return BaseResult.fail("缺少帖子ID");
		}
		
		communityService.deleteThread(user, thread.getThreadId());
		return BaseResult.successResult("succeeded");
		
	}
	
	/**
	 * 更新帖子 TODO	可能不提供编辑功能
	 * @param session
	 * @param thread
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/thread/updateThread", method = RequestMethod.POST)
	public BaseResult<?> updateThread(HttpSession session, @RequestBody Thread thread) throws Exception{
		
//		User user = (User)session.getAttribute(Constants.USER);
		communityService.updateThread(thread);
		return BaseResult.successResult("");
		
	}
	
	/**
	 * 查看帖子详细
	 * @param session
	 * @param threadId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thread/getThreadByThreadId", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<Thread> getThreadByThreadId(HttpSession session, @RequestBody Thread thread) throws Exception{
		
		User user = (User)session.getAttribute(Constants.USER);
		
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		if(sect_id == null){
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		Long threadId = thread.getThreadId();
		
		if (StringUtil.isEmpty(threadId)) {
			return BaseResult.fail("未选中帖子");
		}
		
		Thread ret = communityService.getThreadByTreadId(threadId);
		
		/*
		 * 如果上传文件路径为空，则先更新上传文件路径
		 */
		moveImgsFromTencent2Qiniu(ret);
			
		String attachmentUrl = ret.getAttachmentUrl();
		if (!StringUtil.isEmpty(attachmentUrl)) {
			
			String[]urls = attachmentUrl.split(",");
			
			List<String>imgLinkList = new ArrayList<String>();
			List<String>thumbnailLinkList = new ArrayList<String>();
			
			for (int i = 0; i < urls.length; i++) {
				
				String urlKey = urls[i];
				imgLinkList.add(QiniuUtil.getInstance().getInterlaceImgLink(urlKey, "1"));
				thumbnailLinkList.add(QiniuUtil.getInstance().getThumbnailLink(urlKey, "3", "0"));
				
			}
			
			ret.setImgUrlLink(imgLinkList);
			ret.setThumbnailLink(thumbnailLinkList);
		}
		
		List<ThreadComment>list = communityService.getCommentListByThreadId(threadId);
		
		for (int i = 0; i < list.size(); i++) {
			
			ThreadComment tc = list.get(i);
			if (tc.getCommentUserId()==user.getId()) {
				tc.setIsCommentOwner("true");
			}else {
				tc.setIsCommentOwner("false");
			}
			
		}
		
		ret.setComments(list);
		
		if (ret.getUserId()==user.getId()) {
			ret.setIsThreadOwner("true");
		}else {
			ret.setIsThreadOwner("false");
		}
		
		ret.setHasUnreadComment("false");
		communityService.updateThread(ret);
		return BaseResult.successResult(ret);
		
	}
	
	/**
	 * 上传图片到七牛服务器
	 * @param ret
	 * @param inputStream
	 * @param uploadIds
	 */
	@SuppressWarnings("rawtypes")
	private void upload2Qiniu(Thread ret, String uploadIds ){
		
		InputStream inputStream = null;
		if (!StringUtil.isEmpty(uploadIds)) {
			
			uploadIds = uploadIds.substring(0, uploadIds.length()-1);	//截掉最后一个逗号
			String[]uploadIdArr = uploadIds.split(",");
			
			String uptoken = QiniuUtil.getInstance().getUpToken();	//获取qiniu上传文件的token
			
			log.error("qiniu token :" + uptoken);
			
			String currDate = DateUtil.dtFormat(new Date(), "yyyyMMdd");
			String currTime = DateUtil.dtFormat(new Date().getTime(), "HHMMss");
			String tmpPathRoot = tmpFileRoot+File.separator+currDate+File.separator;
			
			File file = new File(tmpPathRoot);
			if (!file.exists()||!file.isDirectory()) {
				file.mkdirs();
			}
			String keyListStr = "";
			String imgHeight = "";
			String imgWidth = "";
			
			PutExtra extra = new PutExtra();
			File img = null;
			
			String accessToken = systemConfigService.queryWXAToken();
			
			try {
				for (int i = 0; i < uploadIdArr.length; i++) {
					
					String uploadId = uploadIdArr[i];
					int imgcounter = 0;
					inputStream = null;
					while(inputStream==null&&imgcounter<3) {
						inputStream = FileService.downloadFile(uploadId, accessToken);		//下载图片
						if (inputStream==null) {
							log.error("获取图片附件失败。");
						}
						imgcounter++;
					}
					
					if (inputStream==null) {
						log.error("多次从腾讯获取图片失败。");
						return;
					}
					String tmpPath = tmpPathRoot+currTime+"_"+ret.getThreadId()+"_"+i;
					FileService.inputStream2File(inputStream, tmpPath);
					String key = currDate+"_"+currTime+"_"+ret.getThreadId()+"_"+i;
					img = new File(tmpPath);
					
					if (img.exists() && img.getTotalSpace()>0) {

						PutRet putRet = IoApi.putFile(uptoken, key, img, extra);
						log.error("ret msg is : " + putRet.getException());
						log.error("putRet is : " + putRet.toString());
						
						while (putRet.getException()!=null) {
							putRet = IoApi.putFile(uptoken, key, img, extra);
							java.lang.Thread.sleep(100);
						}
						
						boolean isUploaded = false;
						int counter = 0;
						Map map = null;
						while (!isUploaded && counter <3 ) {

							map = QiniuUtil.getInstance().getImgs(domain+key);
							Object error = map.get("error");
							if (error != null) {
								log.error((String)error);
								log.error("start to re-upload ...");
								putRet = IoApi.putFile(uptoken, key, img, extra);
								java.lang.Thread.sleep(100);
							}else {
								isUploaded = true;
								break;
							}
							
							counter++;
						}
						
						if (isUploaded) {
							
							keyListStr+=domain+key;
							
							Integer width = (Integer)map.get("width");
							Integer height = (Integer)map.get("height");
							
							imgWidth+=width;
							imgHeight+=height;
							
							if (i!=uploadIdArr.length-1) {
								keyListStr+=",";
								imgWidth+=",";
								imgHeight+=",";
							}
						}
						
					
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}finally{
				if (inputStream!=null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
			}
		
			ret.setAttachmentUrl(keyListStr);
			ret.setImgHeight(imgHeight);
			ret.setImgWidth(imgWidth);
			communityService.updateThread(ret);	//更新上传文件路径
		
		}
		
		
	}
	
	private void moveImgsFromTencent2Qiniu(Thread ret){
		
		//从腾讯下载用户上传的图片。并放到图片服务器上。
		String uploadIds = ret.getUploadPicId();
		String attachmentUrls = ret.getAttachmentUrl();
		
		if (StringUtil.isEmpty(attachmentUrls)) {
			
			upload2Qiniu(ret, uploadIds);
			
		}else {
		
			if (!StringUtil.isEmpty(uploadIds)) {
				
				if (attachmentUrls.endsWith(",")) {
					attachmentUrls = attachmentUrls.substring(0, attachmentUrls.length()-1);
				}
				String[]uploadIdArr = uploadIds.split(",");
				String[]urlArr = attachmentUrls.split(",");
				
				if (uploadIdArr.length!=urlArr.length) {
					upload2Qiniu(ret, uploadIds);
				}
			}
		}
		
	}
	
	/**
	 * 添加评论
	 * @param session
	 * @param threadId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thread/addComment", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<Thread> addComment(HttpSession session, @RequestBody ThreadComment comment) throws Exception{
		
		User user = (User)session.getAttribute(Constants.USER);
		
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		if(sect_id == null){
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		//更新帖子回复数量及最后评论时间
		Thread thread = communityService.getThreadByTreadId(comment.getThreadId());
		thread.setCommentsCount(thread.getCommentsCount()+1);
		thread.setLastCommentTime(System.currentTimeMillis());
		thread.setHasUnreadComment("true");	//是否有未读评论
		communityService.updateThread(thread);
		
		comment.setToUserId(thread.getUserId());
		comment.setToUserName(thread.getUserName());
		comment.setToUserReaded("false");
		
		ThreadComment retComment = communityService.addComment(user, comment);	//添加评论
		
		
		return BaseResult.successResult(retComment);
		
	}
	
	/**
	 * 删除评论
	 * @param session
	 * @param threadId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/thread/deleteComment", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult deleteComment(HttpSession session, @RequestBody ThreadComment comment) throws Exception{
		
		User user = (User)session.getAttribute(Constants.USER);
		
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		if(sect_id == null){
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		communityService.deleteComment(user, comment.getCommentId());	//添加评论
		
		//更新帖子回复数量
		Thread thread = communityService.getThreadByTreadId(comment.getThreadId());
		thread.setCommentsCount(thread.getCommentsCount()-1);
		communityService.updateThread(thread);
		
		return BaseResult.successResult("succeeded");
		
	}
	
	/**
	 * 获取微信ACCESS_TOKEN
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/thread/getAccessToken", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult getAccessToken(HttpSession session){
		
		User user = (User)session.getAttribute(Constants.USER);
		
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		if(sect_id == null){
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		String access_token = WeixinUtil.getToken();
		return BaseResult.successResult(access_token);
		
	}
	
	/**
	 * 刷新页面图片
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/thread/getImgDetail/{threadId}/{index}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult getImgDetail(HttpSession session, @PathVariable long threadId, @PathVariable int index){
		
		User user = (User)session.getAttribute(Constants.USER);
		
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		if(sect_id == null){
			
			return BaseResult.successResult(new ArrayList<Thread>());
		}
		
		Thread ret = communityService.getThreadByTreadId(threadId);
		String attachmentUrl = ret.getAttachmentUrl();
		String imgHeight = ret.getImgHeight();
		String imgWidth = ret.getImgWidth();
		String[]imgUrls = attachmentUrl.split(",");
		String[]heights = imgHeight.split(",");
		String[]widths = imgWidth.split(",");
		
		Map<String,String>map = new HashMap<String, String>();
		map.put("imgUrl", imgUrls[index]);
		map.put("height", heights[index]);
		map.put("width", widths[index]);
		return BaseResult.successResult(map);
	
	}
	
	/**
	 * 首页获取帖子列表
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thread/getMyPublish", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<List<Thread>> getMyPublish(@ModelAttribute(Constants.USER)User user) throws Exception {
		
		Sort sort = new Sort(Direction.DESC ,  "lastCommentTime", "createDate", "createTime");
		
		List<Thread>list = new ArrayList<Thread>();
		list = communityService.getThreadListByUserId(user.getId(), sort);
		
		for (int i = 0; i < list.size(); i++) {
			
			Thread td = list.get(i);
			String attachmentUrl = td.getAttachmentUrl();
			if (!StringUtil.isEmpty(attachmentUrl)) {
				
				String[]urls = attachmentUrl.split(",");
				
				List<String>imgLinkList = new ArrayList<String>();
				List<String>previewLinkList = new ArrayList<String>();
				
				for (int j = 0; j < (urls.length>3?3:urls.length); j++) {
					
					String urlKey = urls[j];
					imgLinkList.add(QiniuUtil.getInstance().getInterlaceImgLink(urlKey, "1"));
					previewLinkList.add(QiniuUtil.getInstance().getPreviewLink(urlKey, "1", "0"));
					
				}
				
				td.setImgUrlLink(imgLinkList);
				td.setPreviewLink(previewLinkList);
			}
			if (ModelConstant.THREAD_CATEGORY_STORE.equals(td.getThreadCategory())) {
				td.setCategoryImgName("img_store_publish");
				td.setCategoryCnName("二手市场");
			}else {
				td.setCategoryImgName("img_chat_publish");
				td.setCategoryCnName("邻里叽歪");
			}
			
//			if (ModelConstant.THREAD_CATEGORY_OUTDOORS.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_outdoors_publish");
//				td.setCategoryCnName("户外运动");
//			}else if (ModelConstant.THREAD_CATEGORY_PETS.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_dog_publish");
//				td.setCategoryCnName("宠物宝贝");
//			}else if (ModelConstant.THREAD_CATEGORY_CATE.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_cate_publish");
//				td.setCategoryCnName("吃货天地");
//			}else if (ModelConstant.THREAD_CATEGORY_STORE.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_store_publish");
//				td.setCategoryCnName("二手市场");
//			}else if (ModelConstant.THREAD_CATEGORY_EDUCATION.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_education_publish");
//				td.setCategoryCnName("亲自教育");
//			}else if (ModelConstant.THREAD_CATEGORY_SPORTS.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_sports_publish");
//				td.setCategoryCnName("运动达人");
//			}else if (ModelConstant.THREAD_CATEGORY_CHAT.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_chat_publish");
//				td.setCategoryCnName("社区杂谈");
//			}else if (ModelConstant.THREAD_CATEGORY_BEAUTIES.equals(td.getThreadCategory())) {
//				td.setCategoryImgName("img_beautifulgirl_publish");
//				td.setCategoryCnName("都市丽人");
//			}
			
		}
		
		for (int i = 0; i < list.size(); i++) {
			
			Thread td = list.get(i);
			String attachmentUrl = td.getAttachmentUrl();
			if (!StringUtil.isEmpty(attachmentUrl)) {
				
				String[]urls = attachmentUrl.split(",");
				
				List<String>imgLinkList = new ArrayList<String>();
				List<String>thumbnailLinkList = new ArrayList<String>();
				
				for (int j = 0; j < (urls.length>3?3:urls.length); j++) {
					
					String urlKey = urls[j];
					imgLinkList.add(QiniuUtil.getInstance().getInterlaceImgLink(urlKey, "1"));
					thumbnailLinkList.add(QiniuUtil.getInstance().getThumbnailLink(urlKey, "3", "0"));
					
				}
				
				td.setImgUrlLink(imgLinkList);
				td.setThumbnailLink(thumbnailLinkList);
			}
			
		}
		
		
		log.debug("list is : " + list);		
		return BaseResult.successResult(list);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/communityInfo/getCommunityInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<List<CommunityInfo>> getCommunityInfo(HttpSession session){
		
		User user = (User)session.getAttribute(Constants.USER);
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.fail("您还没有填写小区信息。");
		}
		
		if(sect_id == null){
			
			return BaseResult.fail("您还没有填写小区信息。");
		}
		
		Sort sort = new Sort(Direction.ASC , "infoType");
		
		List<CommunityInfo>cityList = communityService.getCommunityInfoByCityIdAndInfoType(user.getCityId(), "0",sort);
		List<CommunityInfo>regionList = communityService.getCommunityInfoByRegionId(user.getCountyId(), sort);
		List<CommunityInfo>sectList = communityService.getCommunityInfoBySectId(sect_id, sort);
		
		List<CommunityInfo>retList = new ArrayList<CommunityInfo>();
		for (int i = 0; i < cityList.size(); i++) {
			
			retList.add(cityList.get(i));
		}
		
		for (int i = 0; i < regionList.size(); i++) {
			
			retList.add(regionList.get(i));
		}
		
		for (int i = 0; i < sectList.size(); i++) {
			
			retList.add(sectList.get(i));
		}	
		
		return BaseResult.successResult(retList);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/annoucement/getAnnoucementList", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<List<Annoucement>> getAnnoucementList(HttpSession session){
		
		User user = (User)session.getAttribute(Constants.USER);
		Long sect_id = null;
		try {
			sect_id = user.getXiaoquId();
		} catch (Exception e) {
			
			return BaseResult.fail("您还没有填写小区信息。");
		}
		
		if(sect_id == null){
			
			return BaseResult.fail("您还没有填写小区信息。");
		}
		
		Sort sort = new Sort(Direction.DESC , "annoucementId");
		List<Annoucement> list = communityService.getAnnoucementList(sort);
		
		return BaseResult.successResult(list);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getUnreadComments", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getUnreadComments(HttpSession session){
		
		User user = (User)session.getAttribute(Constants.USER);
		int unread = 0;
		try {
			
			if(user == null) {
				return BaseResult.successResult(unread);
			}
			
			user = userService.getById(user.getId());
			if(user == null) {
				return BaseResult.successResult(unread);
			}
			
			//TODO 已经在thread表中加了是否有未读评论字段，以后如果数据量大时，直接取thread表中的该字段做判断即可。
			unread = communityService.getUnreadCommentsCount(ModelConstant.COMMENT_STATUS_NORMAL, user.getId());
			
		} catch (Exception e) {

			log.error("getUnreadCommentsError: " + e.getMessage());
		}
		return BaseResult.successResult(unread);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/thread/updateUnreadComment/{threadUserId}/{threadId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult updateUnreadComment(HttpSession session, @PathVariable long threadUserId, @PathVariable long threadId){
		
		User user = (User)session.getAttribute(Constants.USER);
		
		if (threadUserId == user.getId()) {
			
			communityService.updateCommentReaded(threadUserId, threadId);
		}
		
		return BaseResult.successResult("succeeded");
		
	}
}
