package com.weixin.server.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.Manager.MyX509TrustManager;
import com.weixin.server.Manager.pojo.AccessToken;
import com.weixin.server.Manager.pojo.Menu;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: WeixinUtil
 * @Description: TODO(公众平台通用接口工具类) 
 * @author: zhanggd
 * @date 2016年3月2日下午7:13:01
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//菜单查询
	public static String menu_query_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		log.debug("进入getAccessToken(appid={},appsecret={})",appid,appsecret);
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		log.debug("离开getAccessToken()");
		return accessToken;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		log.debug("进入httpRequest()");
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			log.info("向服务器发送https请求");
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			//在android中必须将此项设置为false 
			httpUrlConn.setDoOutput(true);//允许输出流，即允许上传
			httpUrlConn.setDoInput(true);//允许输入流，即允许下载
			httpUrlConn.setUseCaches(false);//不使用缓冲
			// 设置请求方式（GET/POST）
//			if(null!=requestMethod && !requestMethod.equals("")) {
				httpUrlConn.setRequestMethod(requestMethod);
//			}else{
//				httpUrlConn.setRequestMethod("GET"); //使用get请求
//			}
			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		log.debug("离开httpRequest()");
		return jsonObject;
	}
	
	/**
	 * 
	 * @Title: createMenu 
	 * @Description: TODO(菜单实例)
	 * @param menu
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 * @author: zhanggd
	 * @date: 2016年3月11日上午11:46:14
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		log.info(jsonMenu);
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		log.info(jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	public static String queryMenu(String accessToken){
		String result = null;
		String url = menu_query_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = httpRequest(url, "GET", null);
		log.info("return::::"+jsonObject.toString());
		if (null != jsonObject) {
			if (null != jsonObject.get("errcode")) {
//				result = String.valueOf(jsonObject.getInt("errcode"));
				result = "0";
				log.error("查询菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}else{
				result = jsonObject.toString();
			}
		}
		return result;
	}
}