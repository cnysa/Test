package com.weixin.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.servlets.threads.TokenThread;

/**
 * 
 * @ClassName: SignUtil
 * @Description: TODO(请求校验工具类) 
 * @author: zhanggd
 * @date 2016年3月3日下午7:07:09
 */
public class SignUtil {
	private static Logger log = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * 
	 * @Title: checkSignature 
	 * @Description: TODO(验证签名)
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @author: zhanggd
	 * @date: 2016年3月3日下午7:08:17
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		log.debug("进入checkSignature(signature={},timestamp={},nonce="+nonce+")",signature, timestamp);
		String[] arr = { TokenThread.token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			log.error("{}",e);
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		log.debug("离开checkSignature()");
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 
	 * @Title: byteToStr 
	 * @Description: TODO(将字节数组转换为十六进制字符串)
	 * @param byteArray
	 * @return
	 * @author: zhanggd
	 * @date: 2016年3月3日下午7:07:50
	 */
	private static String byteToStr(byte[] byteArray) {
		log.debug("进入byteToStr()");
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		log.debug("离开byteToStr():{}",strDigest);
		return strDigest;
	}

	/**
	 * 
	 * @Title: byteToHexStr 
	 * @Description: TODO(将字节转换为十六进制字符串)
	 * @param mByte
	 * @return
	 * @author: zhanggd
	 * @date: 2016年3月3日下午7:07:33
	 */
	private static String byteToHexStr(byte mByte) {
		log.debug("进入byteToHexStr()");
		
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		log.debug("离开byteToHexStr():{}",s);
		return s;
	}
}
