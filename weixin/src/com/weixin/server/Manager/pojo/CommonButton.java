package com.weixin.server.Manager.pojo;

/**
 * 
 * @ClassName: CommonButton
 * @Description: TODO(普通按钮（子按钮） ) 
 * @author: zhanggd
 * @date 2016年3月11日上午11:42:49
 */
public class CommonButton extends Button{
	private String type;
	private String key;

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
}
