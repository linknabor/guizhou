package com.yumu.hexie.integration.wuye.base;

import java.io.Serializable;

public class BaseQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6790409429193278838L;
	
	private int total_size = 0;
	private int curr_page = 0;
	private int page_size = 0;
	
	public int getTotal_size() {
		return total_size;
	}
	public void setTotal_size(int total_size) {
		this.total_size = total_size;
	}
	public int getCurr_page() {
		return curr_page;
	}
	public void setCurr_page(int curr_page) {
		this.curr_page = curr_page;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	
	
	

}
