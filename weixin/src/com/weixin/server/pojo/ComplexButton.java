package com.weixin.server.pojo;

/**
 * 
 * @ClassName: ComplexButton
 * @Description: TODO(复杂按钮(父按钮)) 
 * @author: zhanggd
 * @date 2016年3月11日上午11:43:09
 */
public class ComplexButton extends Button{
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
