/**
 * @Project Name:weixin
 * @File Name:CjcxAction.java
 * @Package Name:com.weixin.web.action
 * @author zhanggd
 * @Date:2016年5月15日下午5:59:53
 */

package com.weixin.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: CjcxAction
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月15日 下午5:59:53
 */
@Controller
public class CjcxAction extends BaseAction{
	
	@RequestMapping(value="/wx_cjcx")
	public String cjcx(final ModelMap model){
		return "cjcx";
	}
	
	@RequestMapping(value="/wx_cjcx_login")
	public String cjcxLogin(
			final @RequestParam(value = "username", required = true) String username,
			final @RequestParam(value = "password", required = true) String password,
			final ModelMap model) throws IOException{
		log.info("username:"+username);
		log.info("password:"+password);
		Map<String,Object> map = CjcxAction.getLoginCookie(username,password);
		String status = (String)map.get("status");
		if("0".equals(status)){
			model.put("msg", "网络错误");
			model.put("url", "http://moshangren.imwork.net/weixin/wx_cjcx");
			return "result";
		}else if("2".equals(status)){
			model.put("msg", "账号或者密码错误");
			model.put("url", "http://moshangren.imwork.net/weixin/wx_cjcx");
			return "result";
		}
		model.putAll(map);
		return "cjcxResult";
	}
	
	public static Map<String,Object> getLoginCookie(String username, String password) throws IOException {
		CloseableHttpClient httpClient =  HttpClients.createDefault();
		// 登陆URL, POST数据提交地址
		String url = "http://210.44.159.22";
        String loginUrl = url+"/Default2.aspx";
        String referer = url + "/xs_main.aspx?xh="+username;
        String xskscx = url+"/xsdjkscx.aspx?xh="+username+"&xm=%B7%BD%BA%AD&gnmkdm=N121606";
        Map<String,Object> mapResult = new HashMap<String,Object>();
        CloseableHttpResponse response = null;
        CloseableHttpResponse responseget = null;
        //模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		HttpPost httpPost = new HttpPost(loginUrl);
        try {
        	//设置登陆信息，POST提交数据
    		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    		nvps.add(new BasicNameValuePair("__VIEWSTATE", "dDwtMTMxNjk0NzYxNTs7PtSNmeyDsvQQJ46rKmnP1/9Eh4rQ"));
            nvps.add(new BasicNameValuePair("txtUserName", username));
            nvps.add(new BasicNameValuePair("TextBox2", password));
            nvps.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));	//“学生”的 urlEncode编码
            nvps.add(new BasicNameValuePair("Button1", ""));			//“登录”的 urlEncode编码
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
			
        	//第1次get请求
			HttpGet httpGet = new HttpGet(xskscx);
//			httpGet.setHeader("Referer", "http://210.44.159.22/xs_main.aspx?xh=201403061015");
			httpGet.setHeader("Referer", referer);
            responseget = httpClient.execute(httpGet);
            Map<String,String> map2 = printResponse(responseget);
            String entity = (String)map2.get("entity");
            String status2 = (String)map2.get("status");
            if(status2==null || !status2.equals("200") || entity == null){
            	mapResult.put("status", "0");
            	return mapResult;
            }
            mapResult.put("result", getResults(entity));
        	mapResult.put("status", "1");//200
		}finally {
            try {
            	if(response != null){
            		response.close();
            	}
            	if(responseget != null){
            		responseget.close();
            	}
                // 关闭流并释放资源
            	httpClient.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
        }
        return mapResult;
	}
	 public static Map<String,String> printResponse(HttpResponse httpResponse)
		      throws ParseException, IOException {
		    Map<String,String> result = new HashMap<String, String>();
		    if(null == httpResponse){
		    	result.put("status", null);
		    	return result;
		    }
		    // 获取响应消息实体
		    HttpEntity entity = httpResponse.getEntity();
		    // 响应状态
//		    System.out.println("<===================print=======================>");
//		    System.out.println("status:" + httpResponse.getStatusLine().getStatusCode());
		    result.put("status", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
//		    System.out.println("headers:");
//		    HeaderIterator iterator = httpResponse.headerIterator();
//		    while (iterator.hasNext()) {
//		      System.out.println("\t" + iterator.next());
//		    }
		    // 判断响应实体是否为空
		    if (entity != null) {
		      String responseString = EntityUtils.toString(entity);
//		      System.out.println("response length:" + responseString.length());
//		      System.out.println("response content:"+responseString);
		      result.put("entity", responseString);
		    }else{
		    	result.put("entity", null);
		    }
//		    System.out.println("<===================print end=======================>");
		    return result;
		  }
	 
	 public static String[][] getResults(String htmlSource) {
			Map<String,String> result = new HashMap<String, String>();
			int num = 0;
			Document document = Jsoup.parse(htmlSource);
			Elements elements = new Elements(document.getElementById("DataGrid1").getElementsByTag("td"));
			for (int i = 0; i < elements.size(); i++) {
//				System.out.println(elements.get(i).html() + "\t" + elements.get(i+1).html() + "\t" + elements.get(i+2).html() + "\t" + 
//									elements.get(i+3).html() + "\t" + elements.get(i+4).html() + "\t" + elements.get(i+5).html() + "\t" + 
//									elements.get(i+6).html() + "\t" + elements.get(i+7).html() + "\t" + elements.get(i+8).html() + "\t" +
//									elements.get(i+9).html() + "\t");
				result.put("kc_name"+num, elements.get(i+2).html());
				result.put("kc_result"+num, elements.get(i+5).html());
//				System.out.println("----------------------Processing end-----------------------------");
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
			return results;
		}

}
