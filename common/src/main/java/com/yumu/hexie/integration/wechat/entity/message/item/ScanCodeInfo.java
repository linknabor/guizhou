package com.yumu.hexie.integration.wechat.entity.message.item;

import java.io.Serializable;

public class ScanCodeInfo implements Serializable {

	private static final long serialVersionUID = -3179284308278946281L;
	private String ScanType;
	private String ScanResult;
	public String getScanType() {
		return ScanType;
	}
	public void setScanType(String scanType) {
		ScanType = scanType;
	}
	public String getScanResult() {
		return ScanResult;
	}
	public void setScanResult(String scanResult) {
		ScanResult = scanResult;
	}
	
}
