package com.weixin.server.pojo;

/**
 * 
 * @ClassName: ComplexButton
 * @Description: TODO(���Ӱ�ť(����ť)) 
 * @author: zhanggd
 * @date 2016��3��11������11:43:09
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
