/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.common;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;
import com.yumu.hexie.web.BaseResult;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: QiniuController.java, v 0.1 2016年1月7日 上午1:16:59  Exp $
 */
@Controller(value = "qiniuController")
public class QiniuController {
    @Value(value = "${qiniu.access.key}")
    private String accessKey;
    @Value(value = "${qiniu.secret.key}")
    private String secretKey;
    public static final String BUCKET_NAME = "e-shequ";

    @RequestMapping(value = "/api/qiniu/token", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> getPostToken() throws AuthException,
            JSONException {
        Mac mac = new Mac(accessKey, secretKey);
        PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
        return new BaseResult<String>().success(putPolicy.token(mac));
    }
}
