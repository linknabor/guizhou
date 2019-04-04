package com.yumu.hexie.integration.wuye.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.integration.wuye.vo.HexieHouse;

public class HouseListVO  implements Serializable  {
	private static final long serialVersionUID = 715827418453197462L;

	private List<HexieHouse> hou_info;

	public List<HexieHouse> getHou_info() {
		return hou_info;
	}

	public void setHou_info(List<HexieHouse> hou_info) {
		this.hou_info = hou_info;
	}
	
}
