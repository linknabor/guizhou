
package com.yumu.hexie.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;

@Entity
public class User extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private String realName;
	private String name;
	private String tel;
	
	
	/** 来自默认地址，用于服务支持 */
	private long provinceId;
	private long cityId;
	private long countyId;
	private long xiaoquId;//对应region表
	private String county;

	private String xiaoquName;//对应region表
    private Double longitude;
    private Double latitude;
	private long currentAddrId;
	
	private String wuyeId;//对应物业的user_id
	
	/**用户的标识，对当前公众号唯一*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String openid;
	private String memo;
	/**用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。	 */
	private Integer subscribe;
	private int status = 0;//0.初始化  1.绑定手机 2.设定小区 3.绑定房产 4.禁止
	/** 用户的昵称 */
	private String nickname;
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private Integer sex;
	private int age = 20;//新增年龄
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
	private Date subscribe_time;
	private long registerDate;//注册时间
	private String identityCard;
	private int zhima;
	private int lvdou;
	private Integer couponCount;
	private String bindAppId;
	private String bindOpenId;
	
	private String shareCode;
	
	private boolean newRegiste = true;
	private long total_bind = 0;	//总共绑定房屋，初始为0
	private String sect_id;//绑定小区ID 对应region_info表
	private String sect_name;	//绑定房屋所在小区
	private String cell_id;//绑定房屋的ID
	private String cell_addr; 
	private String officeTel;
	
	
	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	
	public long getCurrentAddrId() {
		return currentAddrId;
	}

	public void setCurrentAddrId(long currentAddrId) {
		this.currentAddrId = currentAddrId;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Date getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Date subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	@Transient
	public String getLevel() {
		if (lvdou < 1000) {
			return "游客";
		} else if (lvdou < 2000) {
			return "普通会员";
		} else if (lvdou < 5000) {
			return "银卡会员";
		} else if (lvdou < 10000) {
			return "金卡会员";
		} else {
			return "钻石会员";
		}
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCountyId() {
		return countyId;
	}

	public void setCountyId(long countyId) {
		this.countyId = countyId;
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

	public String getWuyeId() {
		return wuyeId;
	}

	public void setWuyeId(String wuyeId) {
		this.wuyeId = wuyeId;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public long getXiaoquId() {
		return xiaoquId;
	}

	public void setXiaoquId(long xiaoquId) {
		this.xiaoquId = xiaoquId;
	}

	public String getXiaoquName() {
		return xiaoquName;
	}

	public void setXiaoquName(String xiaoquName) {
		this.xiaoquName = xiaoquName;
	}
	
	@Transient
	public String getNoticeName(){
		if(StringUtil.isNotEmpty(realName)) {
			return realName;
		}
		if(StringUtil.isNotEmpty(name)) {
			return name;
		}
		return nickname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public long getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(long registerDate) {
		this.registerDate = registerDate;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	
	@Transient
	public void generateShareCode(){
		shareCode = DigestUtils.md5Hex("UID["+getId()+"]");
	}
	public int getCouponCount() {
		if(couponCount == null) {
			couponCount = 0;
		}
		return couponCount;
	}

	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}

	public boolean isNewRegiste() {
		return newRegiste;
	}

	public void setNewRegiste(boolean isNewRegiste) {
		this.newRegiste = isNewRegiste;
	}

	public String getBindAppId() {
		return bindAppId;
	}

	public void setBindAppId(String bindAppId) {
		this.bindAppId = bindAppId;
	}

	public String getBindOpenId() {
		return bindOpenId;
	}

	public void setBindOpenId(String bindOpenId) {
		this.bindOpenId = bindOpenId;
	}

	public long getTotal_bind() {
		return total_bind;
	}

	public void setTotal_bind(long total_bind) {
		this.total_bind = total_bind;
	}

	public String getSect_id() {
		if(StringUtil.isEmpty(sect_id))
		{
			sect_id = "0";
		}
		return sect_id;
	}

	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}

	public String getCell_id() {
		return cell_id;
	}

	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}

	public String getCell_addr() {
		return cell_addr;
	}

	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}

	public String getSect_name() {
		return sect_name;
	}

	public void setSect_name(String sect_name) {
		this.sect_name = sect_name;
	}

	@Override
	public String toString() {
		return "User [realName=" + realName + ", name=" + name + ", tel=" + tel + ", provinceId=" + provinceId
				+ ", cityId=" + cityId + ", countyId=" + countyId + ", xiaoquId=" + xiaoquId + ", county=" + county
				+ ", xiaoquName=" + xiaoquName + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", currentAddrId=" + currentAddrId + ", wuyeId=" + wuyeId + ", openid=" + openid + ", memo=" + memo
				+ ", subscribe=" + subscribe + ", status=" + status + ", nickname=" + nickname + ", sex=" + sex
				+ ", age=" + age + ", city=" + city + ", country=" + country + ", province=" + province + ", language="
				+ language + ", headimgurl=" + headimgurl + ", subscribe_time=" + subscribe_time + ", registerDate="
				+ registerDate + ", identityCard=" + identityCard + ", zhima=" + zhima + ", lvdou=" + lvdou
				+ ", couponCount=" + couponCount + ", bindAppId=" + bindAppId + ", bindOpenId=" + bindOpenId
				+ ", shareCode=" + shareCode + ", newRegiste=" + newRegiste + ", total_bind=" + total_bind
				+ ", sect_id=" + sect_id + ", sect_name=" + sect_name + ", cell_id=" + cell_id + ", cell_addr="
				+ cell_addr + ", officeTel=" + officeTel + "]";
	}

	

	
}
