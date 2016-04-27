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
	 * ����ͼ�������api�ӿڣ���ȡ���ܻظ����ݣ�������ȡ�Լ�������
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
		// ����httpget����
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
		/** ����ʧ�ܴ��� */
		if(null==result || result.equals("")){
			return "�Բ�����˵�Ļ�����̫�����ˡ���";
		}
			log.info(result);
		try {
			JSONObject json = JSONObject.fromObject(result);//new JSONObject(result);
			if(100000==json.getInt("code")){//�ı�
				result = json.getString("text");
			}
			if(200000==json.getInt("code")){//����
				result = json.getString("url");
			}
//			if(302000==json.getInt("code")){//����
//				result = json.getString("list");
//			}
//			if(308000==json.getInt("code")){//����
//				result = json.getString("list");
//			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
