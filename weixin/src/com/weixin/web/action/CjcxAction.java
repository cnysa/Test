/**
 * @Project Name:weixin
 * @File Name:CjcxAction.java
 * @Package Name:com.weixin.web.action
 * @author zhanggd
 * @Date:2016��5��15������5:59:53
 */

package com.weixin.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.User;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.WeixinUtil;

/**
 * @ClassName: CjcxAction
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��15�� ����5:59:53
 */
@Controller
public class CjcxAction extends BaseAction{
	
	private static Logger log = LoggerFactory.getLogger(CjcxAction.class);
	
	@Autowired
    private UserManager userManagerImpl;//ע��dao
	
	@RequestMapping(value="/wx_cjcx")
	public String wxCjcx(
			final @RequestParam(value = "code", required = false) String code,
			final @RequestParam(value = "state", required = false) String state,
			final ModelMap model){
		log.debug("����wxCjcx(code={},state={})",code,state);
		if(code != null){
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+TokenThread.appid
					+"&secret="+TokenThread.appsecret
					+"&code="+code
					+"&grant_type=authorization_code";
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			String json = jsonObject.toString();
			if (null != jsonObject && json.indexOf("openid")>0) {
				String openid = jsonObject.getString("openid");
				User user = userManagerImpl.queryUser(openid);
				if(user != null && user.getWxUserXh()!=null && user.getWxUserMm()!=null){
					model.put("username", user.getWxUserXh());
					model.put("password", user.getWxUserMm());
				}
			}
		}
		log.debug("�뿪wxCjcx()");
		return "cjcx";
	}
	
	@RequestMapping(value="/wx_cjcx_login")
	public String wxCjcxLogin(
			final @RequestParam(value = "username", required = true) String username,
			final @RequestParam(value = "password", required = true) String password,
			final ModelMap model) throws IOException{
		log.debug("����wxCjcxLogin(username={},password={})",username,password);
		Map<String,Object> map = CjcxAction.cjcxPost(username,password);
		String status = (String)map.get("status");
		if("0".equals(status)){
			model.put("msg", "�������");
			model.put("status", "0");
			model.put("url", TokenThread.weburl+"/weixin/wx_cjcx");
			log.debug("�뿪wxCjcxLogin():result");
			return "result";
		}else if("2".equals(status)){
			model.put("msg", "�˺Ż����������");
			model.put("status", "0");
			model.put("url", TokenThread.weburl+"/weixin/wx_cjcx");
			log.debug("�뿪wxCjcxLogin():result");
			return "result";
		}
		model.putAll(map);
		log.debug("�뿪wxCjcxLogin():cjcxResult");
		return "cjcxResult";
	}
	
	public static Map<String,Object> cjcxPost(String username, String password) throws IOException {
		log.debug("����cjcxPost(username="+username+",password="+password+")");
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		// ��½URL, POST�����ύ��ַ
		String url = "http://210.44.159.22";
        String loginUrl = url+"/Default2.aspx";
        String referer = url + "/xs_main.aspx?xh="+username;
        String xskscx = url+"/xsdjkscx.aspx?xh="+username+"&xm=%B7%BD%BA%AD&gnmkdm=N121606";
        Map<String,Object> mapResult = new HashMap<String,Object>();
        CloseableHttpResponse response = null;
        CloseableHttpResponse responseget = null;
        //ģ���½����ʵ�ʷ�������Ҫ��ѡ�� Post �� Get ����ʽ
		HttpPost httpPost = new HttpPost(loginUrl);
        try {
        	//���õ�½��Ϣ��POST�ύ����
    		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    		nvps.add(new BasicNameValuePair("__VIEWSTATE", "dDwtMTMxNjk0NzYxNTs7PtSNmeyDsvQQJ46rKmnP1/9Eh4rQ"));
            nvps.add(new BasicNameValuePair("txtUserName", username));
            nvps.add(new BasicNameValuePair("TextBox2", password));
            nvps.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));	//��ѧ������ urlEncode����
            nvps.add(new BasicNameValuePair("Button1", ""));			//����¼���� urlEncode����
        	httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        	httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			response = httpClient.execute(httpPost);
            Map<String,String> map1 = printResponse(response);//302
            String status = map1.get("status");
            if(status==null || !status.equals("302")){
            	mapResult.put("status", "0");
            	if(status!=null && status.equals("200")){
            		mapResult.put("status", "2");
            	}
            	return mapResult;
            }
			
        	//��1��get����
			HttpGet httpGet = new HttpGet(xskscx);
			httpGet.setHeader("Referer", referer);
            responseget = httpClient.execute(httpGet);
            Map<String,String> map2 = printResponse(responseget);
            String entity = (String)map2.get("entity");
            String status2 = (String)map2.get("status");
            if(status2==null || !status2.equals("200") || entity == null){
            	mapResult.put("status", "0");
            	return mapResult;
            }
            mapResult.put("result", getCjcxResults(entity));
        	mapResult.put("status", "1");//200
		}finally {
            try {
            	if(response != null){
            		response.close();
            	}
            	if(responseget != null){
            		responseget.close();
            	}
                // �ر������ͷ���Դ
            	httpClient.close();
              } catch (IOException e) {
                log.error("{}",e);
              }
            log.debug("�뿪cjcxPost()");
        }
        return mapResult;
	}
	 public static Map<String,String> printResponse(HttpResponse httpResponse)
		      throws ParseException, IOException {
		 	log.debug("����printResponse()");
			Map<String,String> result = new HashMap<String, String>();
			if(null == httpResponse){
				result.put("status", null);
				return result;
			}
			// ��ȡ��Ӧ��Ϣʵ��
			HttpEntity entity = httpResponse.getEntity();
			// ��Ӧ״̬
			result.put("status", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
			// �ж���Ӧʵ���Ƿ�Ϊ��
			if (entity != null) {
			  String responseString = EntityUtils.toString(entity);
			  result.put("entity", responseString);
			}else{
				result.put("entity", null);
			}
			log.debug("�뿪printResponse()");
			return result;
		}
	 
	 public static String[][] getCjcxResults(String htmlSource) {
		 	log.debug("����getResults()");
			Map<String,String> result = new HashMap<String, String>();
			int num = 0;
			Document document = Jsoup.parse(htmlSource);
			Elements elements = new Elements(document.getElementById("DataGrid1").getElementsByTag("td"));
			for (int i = 0; i < elements.size(); i++) {
				result.put("kc_name"+num, elements.get(i+2).html());
				result.put("kc_result"+num, elements.get(i+5).html());
				i = i+9;
				num++;
			}
			result.put("table_size", String.valueOf(num));
			String[][] results = new String[num][2];
			for(int i=0; i<num; i++){
				results[i][0] = result.get("kc_name"+i);
				results[i][1] = result.get("kc_result"+i);
			}
			results[0][0] = String.valueOf(num);
			log.debug("�뿪getResults()");
			return results;
		}
	 
}
