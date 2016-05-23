/**
 * @Project Name:weixin
 * @File Name:NowAction.java
 * @Package Name:com.weixin.web.action
 * @author zhanggd
 * @Date:2016��5��21������11:08:07
 */

package com.weixin.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.gacl.manager.interfaces.NowVoteManager;
import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.Now;
import com.weixin.gacl.mapping.beans.User;
import com.weixin.gacl.mapping.beans.Vote;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.WeixinUtil;

/**
 * @ClassName: NowAction
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��21�� ����11:08:07
 */
@Controller
public class NowVoteAction extends BaseAction{
	
	@Autowired
	private NowVoteManager nowVoteManagerImpl;
	
	@Autowired
    private UserManager userManagerImpl;//ע��dao
	
	@RequestMapping(value="/wx_vote")
	public String wxNowVote(final ModelMap model){
		log.debug("����wxNowVote()");
		Now now = nowVoteManagerImpl.queryNow();
		if(now != null){
			model.put("voteName", now.getNowName());
			model.put("voteStartTime", now.getNowStartTime());
			model.put("voteEndTime", now.getNowEndTime());
			model.put("voteNumbers", now.getNowNumbers());
			if("0".equals(now.getNowType())){
				model.put("voteType", "ÿ����ֻ��ͶһƱ");
			}else if("1".equals(now.getNowType())){
				model.put("voteType", "ÿ��ÿ��ֻ��ͶһƱ");
			}
			
			Object[] todoArray = JSONArray.fromObject(now.getNowTodo()).toArray();
			int length = todoArray.length;
			List<String> list = new ArrayList<String>();
			for(int i=0; i<length; i++){
				int voteNum = nowVoteManagerImpl.queryOnlyVote(todoArray[i].toString());
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", todoArray[i].toString());
				map.put("num", String.valueOf(voteNum));
				JSONObject jsonObj = JSONObject.fromObject(map);
				list.add(jsonObj.toString());
			}
			model.put("voteTodo", list);
			model.put("status", "1");
			model.put("appid", TokenThread.appid);
			 String weburl = TokenThread.weburl+"/weixin/wx_vote_login.htm";
			model.put("weburl", weburl);
			log.debug("�뿪wxNowVote():{}","nowVote");
			return "nowVote";
		}else{
			model.put("status", "0");
			log.debug("�뿪wxNowVote():{}","nowVote");
			return "nowVote";
		}
		
	}
	
	@RequestMapping(value="/wx_vote_login")
	public String wxNowVote(
			final @RequestParam(value = "code", required = false) String code,
			final @RequestParam(value = "state", required = false) String state,
			final ModelMap model){
		log.debug("����wxNowVote(code={},state={})",code,state);
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
				if(user != null && user.getWxUserXh()!=null && !user.getWxUserXh().equals("") && user.getWxUserMm()!=null && !user.getWxUserMm().equals("")){
					Now now = nowVoteManagerImpl.queryNow();
					if(state == null || state.equals("")){
						model.put("msg", "��û��ѡ��ѡ�������ѡȡ��");
						model.put("status", "0");
						log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
						return "result";
					}
					String statemp = state;
					if(statemp.lastIndexOf(",") == statemp.length()-1){
						statemp = statemp.substring(0, statemp.lastIndexOf(","));
					}
					String[] todos = statemp.split(",");
					if(todos.length > Integer.valueOf(now.getNowNumbers())){
						model.put("msg", "ͶƱ��������Ҫ��");
						model.put("status", "0");
						log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
						return "result";
					}
					Vote[] votes = nowVoteManagerImpl.queryForVote(openid);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String dates = df.format(new Date());
					if("0".equals(now.getNowType())){	//ÿ���˺�ֻ��Ͷһ��
						if(!isDate(now.getNowStartTime(),now.getNowEndTime(),dates)){
							model.put("msg", "����ͶƱ����֮�ڣ�");
							model.put("status", "0");
							log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
							return "result";
						}
						if(votes != null && votes.length > 0){
							model.put("msg", "���Ѿ�����ͶƱ��");
							model.put("status", "0");
							log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
							return "result";
						}
						for(int i=0;i<todos.length;i++){
							nowVoteManagerImpl.addVote(openid, dates, todos[i]);
						}
						model.put("msg", "���Ѿ��ɹ�ͶƱ��");
						model.put("status", "1");
						log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
						return "result";
					}else if("1".equals(now.getNowType())){ //ÿ���˺�ÿһ��ֻ��Ͷһ��
						if(!isDate(now.getNowStartTime(),now.getNowEndTime(),dates)){
							model.put("msg", "����ͶƱ����֮�ڣ�");
							model.put("status", "0");
							log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
							return "result";
						}
						if(votes != null){
							for(int i=0;i<votes.length;i++){
								if(votes[i].getVoteUserTime().equals(dates)){
									model.put("msg", "���Ѿ�����ͶƱ��");
									model.put("status", "0");
									log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
									return "result";
								}
							}
						}
						for(int i=0;i<todos.length;i++){
							nowVoteManagerImpl.addVote(openid, dates, todos[i]);
						}
						model.put("msg", "���Ѿ��ɹ�ͶƱ��");
						model.put("status", "1");
						log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
						return "result";
					}
					model.put("msg", "ͶƱ���ʹ���");
					model.put("status", "0");
					log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
					return "result";
				}else{
					model.put("msg", "���Ȱ�ѧ�ţ�");
					model.put("status", "0");
					model.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appid+"&redirect_uri="+TokenThread.weburl+"/weixin/wx_bindPage.htm&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
					log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
					return "result";
				}
			}
		}
		model.put("msg", "�������");
		model.put("status", "0");
		log.debug("�뿪wxNowVote():{}:{}","result",model.get("msg"));
		return "result";
	}
	
	/**
	 * 
	 * isDate
	 * @Title: isDate
	 * @Description: TODO(�ж��Ƿ���ͶƱ�ڼ�)
	 * @param startTime
	 * @param endTime
	 * @param nowTime
	 * @return: boolean true ��ͶƱ�ڼ� false ����ͶƱ�ڼ�
	 */
	public boolean isDate(String startTime, String endTime, String nowTime){
		log.debug("����isDate(startTime="+startTime+", endTime="+endTime+", nowTime="+nowTime+")");
		String[] start = startTime.split("-");
		String[] end = endTime.split("-");
		String[] now = nowTime.split("-");
		int st = Integer.valueOf(start[0]+start[1]+start[2]);
		int en = Integer.valueOf(end[0]+end[1]+end[2]);
		int no = Integer.valueOf(now[0]+now[1]+now[2]);
		if( st <= no && no <= en){
			log.debug("�뿪isDate():true");
			return true;
		}
		log.debug("�뿪isDate():false");
		return false;
	}
	
	@RequestMapping(value="/nowVotePage")
	public String nowVotePage(final ModelMap model){
		log.debug("����nowVotePage()");
		Now now = nowVoteManagerImpl.queryNow();
		if(now != null){
			model.put("voteName", now.getNowName());
			model.put("voteStartTime", now.getNowStartTime());
			model.put("voteEndTime", now.getNowEndTime());
			model.put("voteNumbers", now.getNowNumbers());
			model.put("voteType", now.getNowType());
			
			Object[] todoArray = JSONArray.fromObject(now.getNowTodo()).toArray();
			int length = todoArray.length;
			List<String> list = new ArrayList<String>();
			for(int i=0; i<length; i++){
				int voteNum = nowVoteManagerImpl.queryOnlyVote(todoArray[i].toString());
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", todoArray[i].toString());
				map.put("num", String.valueOf(voteNum));
				JSONObject jsonObj = JSONObject.fromObject(map);
				list.add(jsonObj.toString());
			}
			model.put("voteTode", list);
		}
		log.debug("�뿪nowVotePage():{}","nowVoteManager");
		return "nowVoteManager";
	}
	
	@RequestMapping(value="/nowVoteAddPage")
	public String nowVoteAddPage(final ModelMap model){
		log.debug("����nowVoteAddPage()");
		log.debug("�뿪nowVoteAddPage():{}","nowVoteManagerAdd");
		return "nowVoteManagerAdd";
	}
	
	@RequestMapping(value="/nowVoteAdd")
	public String nowVoteAdd(
			final @RequestParam(value = "todo", required = true) String name,
			final @RequestParam(value = "selectype", required = true) String selectype,
			final @RequestParam(value = "numselect", required = true) String numselect,
			final @RequestParam(value = "numcount", required = true) String numcount,
			final @RequestParam(value = "timestart", required = true) String timestart,
			final @RequestParam(value = "timeend", required = true) String timeend,
			HttpServletRequest request,
			final ModelMap model){
		log.debug("����nowVoteAdd(log.info(todo:"+name+",selectype:"+selectype+
				",numselect:" + numselect+"numcount:" + numcount+",timestart:" + timestart+
				",timeend:"+timeend+")");
		if(StringUtils.isEmpty(name) || 
				StringUtils.isEmpty(selectype) || 
				StringUtils.isEmpty(numselect) || 
				StringUtils.isEmpty(numcount)){
			model.put("msg", "�������п�ֵ��");
			log.debug("�뿪nowVoteAdd():{}:{}","resultAdmin",model.get("msg"));
			return "resultAdmin";
		}
		int numcou = Integer.valueOf(numcount);
		String[] numcounts = new String[numcou];
		for(int i=0;i<numcou;i++){
			String temp = request.getParameter("200"+i);
			if(StringUtils.isEmpty(temp)){
				model.put("msg", "�������п�ֵ��");
				log.debug("�뿪nowVoteAdd():{}:{}","resultAdmin",model.get("msg"));
				return "resultAdmin";
			}
			numcounts[i] = temp;
		}
		JSONArray jsonarray = JSONArray.fromObject(numcounts);
		if(nowVoteManagerImpl.addNow(selectype, name,jsonarray.toString(),numselect,timestart, timeend)){
			model.put("msg", "����ͶƱ�ɹ���");
			log.debug("�뿪nowVoteAdd():{}:{}","resultAdmin",model.get("msg"));
			return "resultAdmin";
		}
		model.put("msg", "����ͶƱʧ�ܣ�");
		log.debug("�뿪nowVoteAdd():{}:{}","resultAdmin",model.get("msg"));
		return "resultAdmin";
	}
}
