package com.weixin.server.Manager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
 * @ClassName: MyX509TrustManager
 * @Description: TODO(֤�����ι�����������https����) 
 * @author: zhanggd
 * @date 2016��3��11������11:36:57
 */
public class MyX509TrustManager implements X509TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
