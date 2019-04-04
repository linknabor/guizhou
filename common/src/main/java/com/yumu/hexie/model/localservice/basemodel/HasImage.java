/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HasImage.java, v 0.1 2016年5月18日 上午11:45:40  Exp $
 */
public interface HasImage {

//    @Column(length=1023)
//    private String imgUrls;
//    private boolean imageUploaded = true;//默认为空则不需要传图片

    public String getImgUrls();
    public void setImgUrls(String imgUrls) ;
    public boolean isImageUploaded();

    public void setImageUploaded(boolean imageUploaded);
}
