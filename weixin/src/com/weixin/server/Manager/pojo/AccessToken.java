package com.weixin.server.Manager.pojo;

/**
 * 
 * @ClassName: AccessToken
 * @Description: TODO(΢��ͨ�ýӿ�ƾ֤) 
 * @author: zhanggd
 * @date 2016��3��11������11:39:08
 */
public class AccessToken {

	// ��ȡ����ƾ֤
	private String token;
	// ƾ֤��Чʱ�䣬��λ����
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
