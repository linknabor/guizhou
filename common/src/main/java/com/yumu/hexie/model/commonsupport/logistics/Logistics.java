package com.yumu.hexie.model.commonsupport.logistics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.json.JSONException;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.model.BaseModel;
/**快递信息
 */
@Entity
public class Logistics extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String logisticscode; //快递公司编号
	private String logisticsname; //快递公司名称
	private String logisticsno; //快递单号
	private String pollcode; //订阅结果编码
	private String fromaddress; //出发地城市
	private String toaddress; //目的地城市
	private String salt; //签名用随机字符串
	private String pollmessage; //订阅结果描述
	private String monitorstatus; //监控状态
	private String monitormessage; //监控状态消息
	private String signstatus; //签收状态
	private String description; //快递描述
	
	@Transient
	private List<Note> description_array; //快递描述
	
	private String remark; //备注
	private String orderno; //订单编号
	private String ischeck; //是否签收
	
	public static class Note{
		private String time;//": "2015-06-13 10:38:59",
		private String context;//": "签收人是：已签收",
		private String ftime;//": "2015-06-13 10:38:59"
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		public String getFtime() {
			return ftime;
		}
		public void setFtime(String ftime) {
			this.ftime = ftime;
		}
		
	}	

	
	public List<Note> getDescription_array() {
		return description_array;
	}
//	public void setDescription_array(List<Note> description_array) {
//		this.description_array = description_array;
//	}
	public String getLogisticscode() {
		return logisticscode;
	}
	public void setLogisticscode(String logisticscode) {
		this.logisticscode = logisticscode;
	}
	public String getLogisticsname() {
		return logisticsname;
	}
	public void setLogisticsname(String logisticsname) {
		this.logisticsname = logisticsname;
	}
	public String getLogisticsno() {
		return logisticsno;
	}
	public void setLogisticsno(String logisticsno) {
		this.logisticsno = logisticsno;
	}
	public String getPollcode() {
		return pollcode;
	}
	public void setPollcode(String pollcode) {
		this.pollcode = pollcode;
	}
	public String getFromaddress() {
		return fromaddress;
	}
	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}
	public String getToaddress() {
		return toaddress;
	}
	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPollmessage() {
		return pollmessage;
	}
	public void setPollmessage(String pollmessage) {
		this.pollmessage = pollmessage;
	}
	public String getMonitorstatus() {
		return monitorstatus;
	}
	public void setMonitorstatus(String monitorstatus) {
		this.monitorstatus = monitorstatus;
	}
	public String getMonitormessage() {
		return monitormessage;
	}
	public void setMonitormessage(String monitormessage) {
		this.monitormessage = monitormessage;
	}
	public String getSignstatus() {
		return signstatus;
	}
	public void setSignstatus(String signstatus) {
		this.signstatus = signstatus;
	}
	public String getDescription() {
		return description;
	}
	@SuppressWarnings("unchecked")
	public void setDescription(String description) {
		this.description = description;
		List<Note> list = new ArrayList<Note>();
		try {
			list = (List<Note>) JacksonJsonUtil.jsonToBean(this.description,Object.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.description_array = list;
		
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	
	
}
