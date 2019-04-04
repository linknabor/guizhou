package com.yumu.hexie.integration.amap.vo;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.distribution.region.AmapAddress;

public class AmapQueryResult implements Serializable {
	private static final long serialVersionUID = -301401143450240218L;
	private String info; //返回状态 status=1, info返回“ok”，status=0时，info返回错误信息
	private int status; //返回状态:0失败；1成功
	private int count; //返回结果总条目
	private List<AmapAddress> datas;
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<AmapAddress> getDatas() {
		return datas;
	}
	public void setDatas(List<AmapAddress> datas) {
		this.datas = datas;
	}

}
