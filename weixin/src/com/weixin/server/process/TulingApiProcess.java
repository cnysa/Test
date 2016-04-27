package com.weixin.server.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TulingApiProcess {

	private static Logger log = LoggerFactory.getLogger(TulingApiProcess.class);

	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param content
	 * @return
	 */
	public static String getTulingResult(String content){

		String apiUrl = "http://www.tuling123.com/openapi/api?key=6ac61db4ff29ac2487cbd0e6cd982f3a&info=";
		String param = "";
		try {
			param = apiUrl+URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 发送httpget请求
		HttpGet request = new HttpGet(param);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
			}else{
				log.info("no 200");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/** 请求失败处理 */
		if(null==result || result.equals("")){
			return "对不起，你说的话真是太高深了……";
		}
			log.info(result);
		try {
			JSONObject json = JSONObject.fromObject(result);//new JSONObject(result);
			if(100000==json.getInt("code")){//文本
				result = json.getString("text");
			}
			if(200000==json.getInt("code")){//链接
				result = json.getString("url");
			}
//			if(302000==json.getInt("code")){//新闻
//				result = json.getString("list");
//			}
//			if(308000==json.getInt("code")){//菜谱
//				result = json.getString("list");
//			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
