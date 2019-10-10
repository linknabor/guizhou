/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.user.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.BeanUtils;

import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.view.BottomIcon;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: UserInfo.java, v 0.1 2016年2月2日 上午11:30:23  Exp $
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 4808669460780339640L;
    private String realName;
    private String name;
    private String tel;
    
    private boolean isRepairOperator = false;
    
    private Double longitude;
    private Double latitude;
    private long currentAddrId;
    
    private String wuyeId;//对应物业的user_id

    /**用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。     */
    private Integer subscribe;
    private int status = 0;//0.初始化  1.绑定手机 2.设定小区 3.绑定房产 4.禁止
    /** 用户的昵称 */
    private String nickname;
    /** 用户所在城市 */
    private String city;
    /** 用户所在国家 */
    private String country;
    /** 用户所在省份 */
    private String province;
    /** 用户的语言，简体中文为zh_CN */
    private String language;
    /** 用户头像 */
    private String headimgurl;
    /** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
    private int zhima;
    private int lvdou;
    private Integer couponCount;
    
    private String shareCode;
    
    private String xiaoquName;
    private long id;
    private String officeTel;
    
    private String sectId;//小区id
	private String cspId;//公司
 	private Map<?, ?> cfgParam = new HashMap<>();
 	private List<BottomIcon> iconList = new ArrayList<BottomIcon>();
 	
 	private String qrCode;

 	
	public String getSectId() {
		return sectId;
	}
	public void setSectId(String sectId) {
		this.sectId = sectId;
	}
	public String getCspId() {
		return cspId;
	}
	public void setCspId(String cspId) {
		this.cspId = cspId;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public UserInfo(){}
    public UserInfo(User user){
        BeanUtils.copyProperties(user, this);
    }

    public UserInfo(User user,boolean isOperator){
        BeanUtils.copyProperties(user, this);
        this.isRepairOperator = isOperator;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public long getCurrentAddrId() {
        return currentAddrId;
    }

    public void setCurrentAddrId(long currentAddrId) {
        this.currentAddrId = currentAddrId;
    }

    public String getWuyeId() {
        return wuyeId;
    }

    public void setWuyeId(String wuyeId) {
        this.wuyeId = wuyeId;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getZhima() {
        return zhima;
    }

    public void setZhima(int zhima) {
        this.zhima = zhima;
    }

    public int getLvdou() {
        return lvdou;
    }

    public void setLvdou(int lvdou) {
        this.lvdou = lvdou;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public boolean isRepairOperator() {
        return isRepairOperator;
    }

    public void setRepairOperator(boolean isRepairOperator) {
        this.isRepairOperator = isRepairOperator;
    }
    public String getXiaoquName() {
        return xiaoquName;
    }
    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
	public Map<?, ?> getCfgParam() {
		return cfgParam;
	}
	public void setCfgParam(Map<?, ?> cfgParam) {
		this.cfgParam = cfgParam;
	}
	public List<BottomIcon> getIconList() {
		return iconList;
	}
	public void setIconList(List<BottomIcon> iconList) {
		this.iconList = iconList;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
    
    
}
