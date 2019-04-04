package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//消息
@Entity
public class PointRecord extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private int point;
	private long userId;
	private String memo;
	private int type ;//0 芝麻 1绿豆
	private int reason;//发放原因类型 0.关注 1.绑定手机号 2.绑定房子 3.到家服务 4. 发起拼单 5. 参与拼单 6.单个拼单 7.物业缴费
	private String keyStr;
	

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

}
