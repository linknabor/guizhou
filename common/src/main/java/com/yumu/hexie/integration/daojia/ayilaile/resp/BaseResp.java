package com.yumu.hexie.integration.daojia.ayilaile.resp;

import java.io.Serializable;

public class BaseResp  implements Serializable {

	private static final long serialVersionUID = 2243368998351186024L;
	private int nResult;
	private String strResultMessage;
	public int getnResult() {
		return nResult;
	}
	public void setnResult(int nResult) {
		this.nResult = nResult;
	}
	public String getStrResultMessage() {
		return strResultMessage;
	}
	public void setStrResultMessage(String strResultMessage) {
		this.strResultMessage = strResultMessage;
	}
	public boolean isSuccess(){
		return nResult == 1;
	}
}
