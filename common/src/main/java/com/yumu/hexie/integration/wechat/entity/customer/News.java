package com.yumu.hexie.integration.wechat.entity.customer;

import java.util.List;

/**
 * 多图文消息对象
 */
public class News {
	
	/**
	 * 多条图文消息信息列表，默认第一个item为大图
	 */
	private List<Article> Articles;

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

	public News(List<Article> articles) {
		super();
		Articles = articles;
	}

	public News() {
		super();
	}
	
}
