package com.weixin.server.Manager.pojo;

/**
 * 
 * @ClassName: AccessToken
 * @Description: TODO(微信通用接口凭证) 
 * @author: zhanggd
 * @date 2016年3月11日上午11:39:08
 */
public class AccessToken {

	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
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
