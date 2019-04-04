package com.yumu.hexie.model.distribution.region;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//商户
@Entity
public class Merchant  extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private String name;
	private String merchantNo;
	private String address;
	private String description;
	
	private String merchantType;
	private int productType;//与服务单关联，
	private String enterDate;
	private int settleTime;
	
	private String accountName;
	private String bankName;
	private String accountNo;
	
	private String invoiceType;//开票类型，增票普票等
	
	private String taxPayerNo;
	private String accountCycle;//结算周期,日结0，周结1，月结2
	

	private int status;//0.未审核  1.正常
	
	private float platformFeeRate = 0;

	//新增字段保持与后台一致
	private String tel;//电话
	private String email; //邮件
	private String exitDate;//商户失效时间
	
	

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPlatformFeeRate() {
		return platformFeeRate;
	}
	public void setPlatformFeeRate(float platformFeeRate) {
		this.platformFeeRate = platformFeeRate;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getEnterDate() {
		return enterDate;
	}
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}
	public int getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(int settleTime) {
		this.settleTime = settleTime;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getTaxPayerNo() {
		return taxPayerNo;
	}
	public void setTaxPayerNo(String taxPayerNo) {
		this.taxPayerNo = taxPayerNo;
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public String getAccountCycle() {
		return accountCycle;
	}
	public void setAccountCycle(String accountCycle) {
		this.accountCycle = accountCycle;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExitDate() {
		return exitDate;
	}
	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}
}
