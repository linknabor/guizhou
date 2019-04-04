package com.yumu.hexie.integration.daojia.ayilaile.vo;

import java.io.Serializable;

import com.yumu.hexie.common.util.StringUtil;

public class DictionaryVO implements Serializable {

	private static final long serialVersionUID = -3771190283431183728L;

//	"guidDictionaryID": "76542612-6756-4a65-ad76-bf5c1367c3af",
//    "guidDictionaryGroupID": "f65efbe6-861a-47e3-b30c-54d9437777e1",
//    "strDictionaryName": "高级A",
//    "strDictionaryIntro": "高级A:10800元/月",
//    "strDictionaryOther": "双胞胎、早产、珍贵婴增加30%服务费；n持有母婴护理师证；n二年以上母婴护理经验；n两份以上母婴护理工作证明；n90%以上母婴护理合同执行率。",
//    "strDictionaryGroupName": "月嫂薪金范围",
	private int row;
	private String guidDictionaryID;
	private String guidDictionaryGroupID;
	private String strDictionaryName;
	private String strDictionaryIntro;
	private String strDictionaryOther;
	private String strDictionaryGroupName;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getGuidDictionaryID() {
		return guidDictionaryID;
	}
	public void setGuidDictionaryID(String guidDictionaryID) {
		this.guidDictionaryID = guidDictionaryID;
	}
	public String getGuidDictionaryGroupID() {
		return guidDictionaryGroupID;
	}
	public void setGuidDictionaryGroupID(String guidDictionaryGroupID) {
		this.guidDictionaryGroupID = guidDictionaryGroupID;
	}
	public String getStrDictionaryName() {
		return strDictionaryName;
	}
	public void setStrDictionaryName(String strDictionaryName) {
		this.strDictionaryName = strDictionaryName;
	}
	public String getStrDictionaryIntro() {
		return strDictionaryIntro;
	}
	public void setStrDictionaryIntro(String strDictionaryIntro) {
		this.strDictionaryIntro = strDictionaryIntro;
	}
	public String getStrDictionaryOther() {
		return strDictionaryOther;
	}
	public void setStrDictionaryOther(String strDictionaryOther) {
		if(StringUtil.isNotEmpty(strDictionaryOther)) {
			strDictionaryOther = strDictionaryOther.replaceAll("；n", "</li><li>");
		}
		this.strDictionaryOther = strDictionaryOther;
	}
	public String getStrDictionaryGroupName() {
		return strDictionaryGroupName;
	}
	public void setStrDictionaryGroupName(String strDictionaryGroupName) {
		this.strDictionaryGroupName = strDictionaryGroupName;
	}
	
}
