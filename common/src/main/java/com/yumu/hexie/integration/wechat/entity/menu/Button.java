package com.yumu.hexie.integration.wechat.entity.menu;

/**
 * 按钮
 */
public class Button {

	/**
	 * 按钮名称
	 */
	private String name;

	/**
	 * 按钮类型
	 */
	private String type;

	/**
	 * 按钮key值
	 */
	private String key;

	/**
	 * 按钮url
	 */
	private String url;
	
	private String value;

	/**
	 * 子按钮列表
	 */
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Button(String name, String type, String key, String url,String value,
			Button[] sub_button) {
		super();
		this.name = name;
		this.type = type;
		this.key = key;
		this.url = url;
		this.value = value;
		this.sub_button = sub_button;
	}

	public Button() {
		super();
	}

}
