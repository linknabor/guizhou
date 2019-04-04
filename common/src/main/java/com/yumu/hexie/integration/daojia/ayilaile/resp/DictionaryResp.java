package com.yumu.hexie.integration.daojia.ayilaile.resp;

import java.util.List;

import com.yumu.hexie.integration.daojia.ayilaile.vo.DictionaryVO;

public class DictionaryResp extends BaseResp {
	private static final long serialVersionUID = -5243403183976637115L;
	private int nRecordCount;
	private List<DictionaryVO> data;

	public int getnRecordCount() {
		return nRecordCount;
	}

	public void setnRecordCount(int nRecordCount) {
		this.nRecordCount = nRecordCount;
	}

	public List<DictionaryVO> getData() {
		return data;
	}

	public void setData(List<DictionaryVO> data) {
		this.data = data;
	}

}
