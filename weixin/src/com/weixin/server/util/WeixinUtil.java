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
 * @Description: TODO(����ƽ̨ͨ�ýӿڹ�����) 
 * @author: zhanggd
 * @date 2016��3��2������7:13:01
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	
	// ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// �˵�������POST�� ��100����/�죩
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//�˵���ѯ
	public static String menu_query_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * ��ȡaccess_token
	 * 
	 * @param appid ƾ֤
	 * @param appsecret ��Կ
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		log.debug("����getAccessToken(appid={},appsecret={})",appid,appsecret);
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// �������ɹ�
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// ��ȡtokenʧ��
				log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		log.debug("�뿪getAccessToken()");
		return accessToken;
	}

	/**
	 * ����https���󲢻�ȡ���
	 * 
	 * @param requestUrl �����ַ
	 * @param requestMethod ����ʽ��GET��POST��
	 * @param outputStr �ύ������
	 * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		log.debug("����httpRequest()");
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			log.info("�����������https����");
			//����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// ������SSLContext�����еõ�SSLSocketFactory����
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			//��android�б��뽫��������Ϊfalse 
			httpUrlConn.setDoOutput(true);//������������������ϴ�
			httpUrlConn.setDoInput(true);//����������������������
			httpUrlConn.setUseCaches(false);//��ʹ�û���
			// ��������ʽ��GET/POST��
//			if(null!=requestMethod && !requestMethod.equals("")) {
				httpUrlConn.setRequestMethod(requestMethod);
//			}else{
//				httpUrlConn.setRequestMethod("GET"); //ʹ��get����
//			}
			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}
			// ����������Ҫ�ύʱ
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// ע������ʽ����ֹ��������
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// �����ص�������ת�����ַ���
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// �ͷ���Դ
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		log.debug("�뿪httpRequest()");
		return jsonObject;
	}
	
	/**
	 * 
	 * @Title: createMenu 
	 * @Description: TODO(�˵�ʵ��)
	 * @param menu
	 * @param accessToken ��Ч��access_token
	 * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
	 * @author: zhanggd
	 * @date: 2016��3��11������11:46:14
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// ƴװ�����˵���url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// ���˵�����ת����json�ַ���
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// ���ýӿڴ����˵�
		log.info(jsonMenu);
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		log.info(jsonObject.toString());
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
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
				log.error("��ѯ�˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}else{
				result = jsonObject.toString();
			}
		}
		return result;
	}
}