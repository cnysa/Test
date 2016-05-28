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
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	public List<String> getXnXq(String username){
		Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
		String start = username.substring(0, 4);
		int startyear = Integer.valueOf(start);
		if(startyear < 2001 || startyear > year){
			return null;
		}
		int leng = 0;
		if(month >= 9){
			leng = (year - startyear)*2+1;
		}else{
			leng = (year - startyear)*2;
		}
		
		List<String> result = new ArrayList<String>();
		for(int i=0;i<leng && i<8;i++){
			if(i%2 == 0){
				result.add(i,String.valueOf(startyear+i/2)+"-"+String.valueOf(startyear+i/2+1)+"  ��һѧ��");
			}else{
				result.add(i,String.valueOf(startyear+i/2)+"-"+String.valueOf(startyear+i/2+1)+"  �ڶ�ѧ��");
			}
		}
		result.add(leng,"��У�ɼ�");
		return result;
	}
	
	@RequestMapping(value="/wx_cjcx_login")
	public String wxCjcxLogin(
			final @RequestParam(value = "username", required = true) String username,
			final @RequestParam(value = "password", required = true) String password,
			final ModelMap model) throws IOException{
		log.debug("����wxCjcxLogin(username={},password={})",username,password);
		String xn="";
		String xq="";
		Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR); 
        int month = c.get(Calendar.MONTH);
		if(month>=9){
        	xn = String.valueOf(year)+"-"+String.valueOf(year+1);
        	xq = "1";
        }else{
        	xn = String.valueOf(year-1)+"-"+String.valueOf(year);
        	xq = "2";
        }
		Map<String,Object> map = CjcxAction.cjcxPost(username,password,xn,xq);
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
		List<String> xnxq = getXnXq(username);
		model.put("xnxq", xnxq);
		model.put("nowXnXq", xnxq.get(xnxq.size()-2));
		model.put("username", username);
		model.put("password", password);
		log.debug("�뿪wxCjcxLogin():cjcxResult");
		return "cjcxResult";
	}
	
	@RequestMapping(value="/wx_cjcxXnXq", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String cjcxXnXq(
			final @RequestParam(value = "xnxq", required = true) String xnxq,
			final @RequestParam(value = "username", required = true) String username,
			final @RequestParam(value = "password", required = true) String password,
			final ModelMap model) throws IOException{
		log.debug("����cjcxXnXq(xnxq={},username={})",xnxq,username);
		String xn = "";
		String xq = "";
		if(!xnxq.equals("��У�ɼ�")){
			String[] fl = xnxq.split("  ");
			xn = fl[0];
			if(fl[1].indexOf("һ")>0){
				xq = "1";
			}else{
				xq = "2";
			}
		}
		Map<String,Object> map = CjcxAction.cjcxPost(username,password,xn,xq);
		log.debug("�뿪cjcxXnXq(){}",model.get("msg"));
		return JSONObject.fromObject(map).toString();
	}
	
	public static Map<String,Object> cjcxPost(String username, String password,String xn,String xq) throws IOException {
		log.debug("����cjcxPost(username="+username+",password="+password+")");
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		// ��½URL, POST�����ύ��ַ
		String url = "http://210.44.159.22";
        String loginUrl = url+"/Default2.aspx";
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
			
            HttpPost cjcxpost = new HttpPost(url+"/xscj.aspx?xh="+username+"&xm=%D5%C5%B9%FA%B6%B0&gnmkdm=N121605");
            List <NameValuePair> para = new ArrayList <NameValuePair>();
            para.add(new BasicNameValuePair("__VIEWSTATE","dDwtNjI5MTUzMDY1O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8MTU+O2k8MTc+O2k8MjM+O2k8MjU+O2k8Mjc+O2k8Mjk+O2k8MzA+O2k8MzI+O2k8MzQ+O2k8MzY+O2k8NDY+O2k8NTA+Oz47bDx0PHQ8O3Q8aTwxNz47QDxcZTsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjsyMDE2LTIwMTc7PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzs+Pjs+Ozs+O3Q8cDw7cDxsPG9uY2xpY2s7PjtsPHByZXZpZXcoKVw7Oz4+Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjIwMTIwMzAxMTIxNDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5byg5Zu95qCLOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrkv6Hmga/lrabpmaI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86K6h566X5py656eR5a2m5LiO5oqA5pyvOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJrorqHnp5ExMi0zOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8U0RJTEk7Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+Oz4O8rrthgfvH8TmsdXbqM6vuSznrQ=="));
            para.add(new BasicNameValuePair("ddlXN", xn));
            para.add(new BasicNameValuePair("ddlXQ", xq));
            para.add(new BasicNameValuePair("txtQSCJ", "0"));
            para.add(new BasicNameValuePair("txtZZCJ", "100"));
            para.add(new BasicNameValuePair("Button1", "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF"));
            cjcxpost.setEntity(new UrlEncodedFormEntity(para,"UTF-8"));
            cjcxpost.setHeader("Referer", url+"/xscj.aspx?xh="+username+"&xm=%D5%C5%B9%FA%B6%B0&gnmkdm=N121605");
			responseget = httpClient.execute(cjcxpost);
            Map<String,String> map2 = printResponse(responseget);//302
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
			  System.out.println(responseString);
			  result.put("entity", responseString);
			}else{
				result.put("entity", null);
			}
			log.debug("�뿪printResponse()");
			return result;
		}
	 
	 public static String[][] getCjcxResults(String htmlSource) {
		 	log.debug("����getResults()");
		 	log.info(htmlSource);
			Map<String,String> result = new HashMap<String, String>();
			int num = 0;
			Document document = Jsoup.parse(htmlSource);
			Elements elements = new Elements(document.getElementById("DataGrid1").getElementsByTag("td"));
			for (int i = 0; i < elements.size(); i++) {
				result.put("kc_name"+num, elements.get(i+1).html());
				result.put("kc_result"+num, elements.get(i+3).html());
				i = i+8;
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
