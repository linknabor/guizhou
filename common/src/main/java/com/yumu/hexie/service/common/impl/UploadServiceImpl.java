/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.IOUtils;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.qiniu.util.QiniuUtil;
import com.yumu.hexie.integration.wechat.service.FileService;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.localservice.repair.RepairOrderRepository;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.common.UploadService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: UploadServiceImpl.java, v 0.1 2016年1月7日 上午2:04:59  Exp $
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Value(value = "${tmpfile.dir}")
    private String              tmpFileRoot;

    @Value(value = "${qiniu.domain}")
    private String              domain;
    
    @Inject
    private RepairOrderRepository repairOrderRepository;
    
    @Inject
    private SystemConfigService systemConfigService;

    /** 
     * @param order
     * @see com.yumu.hexie.service.common.UploadService#updateRepairImg(com.yumu.hexie.model.localservice.repair.RepairOrder)
     */
    @Override
    @Async
    public void updateRepairImg(RepairOrder order) {
        if (order.isImageUploaded()
                ||( StringUtil.isEmpty(order.getImgUrls()) 
                && StringUtil.isEmpty(order.getCommentImgUrls()))) {
            return;
        }
        String imgUrls = moveImges(order.getImgUrls());
        String commentImgUrls = moveImges(order.getCommentImgUrls());
        
        RepairOrder nOrder = repairOrderRepository.findOne(order.getId());
        nOrder.setImgUrls(imgUrls);
        nOrder.setCommentImgUrls(commentImgUrls);
        repairOrderRepository.save(nOrder);
    }

    private String moveImges(String imgUrls) {
        if(StringUtil.isEmpty(imgUrls)) {
            return "";
        }
        String newImgUrls = "";
        String[] urls = imgUrls.split(RepairOrder.IMG_SPLIT);
        for (String url : urls) {
            if(StringUtils.isEmpty(url)) {
                continue;
            }
            if (url.indexOf("http")<0) {
                String qiniuUrl = uploadFileToQiniu(downloadFromWechat(url));
                if(!StringUtils.isEmpty(qiniuUrl)) {
                    newImgUrls += qiniuUrl;
                } else {
                    newImgUrls += url;
                }
            } else {
                newImgUrls += url;
            }
            newImgUrls +=",";
        }
        return newImgUrls;
    }

    private String uploadFileToQiniu(File file) {

        if(file == null || !file.exists()){
            return "";
        }
        PutExtra extra = new PutExtra();
        String uptoken = QiniuUtil.getInstance().getUpToken();

        if (file.exists() && file.getTotalSpace() > 0) {
            PutRet putRet = IoApi.putFile(uptoken, DateUtil.dtFormat(new Date(),"yyyyMMddHHmmssSSS")+(int)(Math.random()*1000), file, extra);
            log.error("上传图片：StatusCode" + putRet.getStatusCode()+"[key] "+putRet.getKey()
                +"[hash] "+putRet.getHash());
            if(putRet.getStatusCode() == 200 && !StringUtils.isEmpty(putRet.getKey())) {
                return domain + putRet.getKey();
            }
        }
        return "";
    }

    //从微信服务器上下载
    private File downloadFromWechat(String mediaId) {

        InputStream inputStream = null;

        String currDate = DateUtil.dtFormat(new Date(), "yyyyMMdd");
        String tmpPathRoot = tmpFileRoot + File.separator + currDate + File.separator;

        File file = new File(tmpPathRoot);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        File mediaFile = null;
        String accessToken = systemConfigService.queryWXAToken();
        try {
            int imgcounter = 0;
            inputStream = null;
            while (inputStream == null && imgcounter < 3) {
                inputStream = FileService.downloadFile(mediaId, accessToken); //重试3次
                if (inputStream == null) {
                    log.error("获取图片附件失败。" + mediaId);
                }
                imgcounter++;
            }

            if (inputStream == null) {
                log.error("多次从腾讯获取图片失败。");
                return null;
            }
            String tmpPath = tmpPathRoot + System.currentTimeMillis();
            FileService.inputStream2File(inputStream, tmpPath);
            mediaFile = new File(tmpPath);

            if (!mediaFile.exists() || mediaFile.getTotalSpace() <= 0) {
                mediaFile = null;
            }
            log.error("下载图片：" + tmpPath);
        } catch (Exception e) {
            log.error("获取图片附件异常。" + mediaId, e);
        } finally {
            IOUtils.closeSilently(inputStream);
        }
        return mediaFile;

    }
}
