/**
 * @Project Name:weixin
 * @File Name:MatchAction.java
 * @Package Name:com.weixin.web.action
 * @author zhanggd
 * @Date:2016年5月19日下午10:15:03
 */

package com.weixin.web.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.gacl.manager.interfaces.MatchManager;
import com.weixin.gacl.mapping.beans.Match;

/**
 * @ClassName: MatchAction
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月19日 下午10:15:03
 */
@Controller
public class MatchAction extends BaseAction{
	
	@Autowired
	private MatchManager matchManagerImpl;
	
	@RequestMapping(value="/wx_match")
	public String wxMatch(final ModelMap model){
		Match[] matchs = matchManagerImpl.queryAll();
		model.put("matchs", matchs);
		return "match";
	}
	
	@RequestMapping(value="/wx_matchPage")
	public String wxMatchPage(
			final @RequestParam(value = "matchId", required = true) String matchId,
			final ModelMap model){
		if(matchId == null || matchId.equals("")){
			model.put("msg", "网络错误");
			model.put("url", "http://moshangren.imwork.net/weixin/wx_match");
			return "result";
		}
		Match matchs = matchManagerImpl.queryMatch(matchId);
		model.put("matchs", matchs);
		return "matchWxPage";
	}
	
	@RequestMapping(value="/matchManager")
	public String matchManager(final ModelMap model){
		Match[] matchs = matchManagerImpl.queryAll();
		model.put("matchs", matchs);
		return "matchManager";
	}
	
	@RequestMapping(value="/matchAddPage")
	public String matchManagerAddPage(
			final @RequestParam(value = "matchId", required = false) String matchId,
			final ModelMap model){
		if(matchId!=null && !matchId.equals("")){
			model.put("matchId", matchId);
			Match match = matchManagerImpl.queryMatch(matchId);
			model.put("matchName", match.getMatchName());
			model.put("matchUrl", match.getMatchUrl());
			model.put("matchGroup", match.getMatchGroup());
			String todo = match.getMatchTodo().replaceAll("<br>","\r\n").replaceAll("&nbsp;"," ").replaceAll("\'","\"");
			model.put("matchTodo", todo);
		}
		return "matchManagerAdd";
	}
	
	@RequestMapping(value="/matchUpdate")
	public String matchManagerUpdate(
			final @RequestParam(value = "matchName", required = true) String matchName,
			final @RequestParam(value = "matchTodo", required = true) String matchTodo,
			final @RequestParam(value = "matchUrl", required = true) String matchUrl,
			final @RequestParam(value = "matchGroup", required = true) String matchGroup,
			final @RequestParam(value = "matchId", required = false) String matchId,
			final ModelMap model){
		String todo;
		log.info(matchName);
		log.info(matchId);
		log.info(matchUrl);
		log.info(matchGroup);
		todo = matchTodo.replaceAll("\\r\\n","<br>").replaceAll(" ","&nbsp;").replaceAll("\"","\'");
		log.info(todo);
		log.info("length: "+todo.length());
		if(matchManagerImpl.queryMatch(matchId)==null){
			model.put("msg", "比赛信息修改失败！");
			return "resultAdmin";
		}
		if(!matchManagerImpl.updateMatch(matchId, matchName, todo, matchUrl, matchGroup)){
			model.put("msg", "比赛信息修改失败！");
			return "resultAdmin";
		}
		Match[] matchs = matchManagerImpl.queryAll();
		model.put("matchs", matchs);
		return "matchManager";
	}
	
	@RequestMapping(value="/matchAdd")
	public String matchManagerAdd(
			final @RequestParam(value = "matchName", required = true) String matchName,
			final @RequestParam(value = "matchTodo", required = true) String matchTodo,
			final @RequestParam(value = "matchUrl", required = true) String matchUrl,
			final @RequestParam(value = "matchGroup", required = true) String matchGroup,
			final ModelMap model){
		String matchId = UUID.randomUUID().toString();
		String todo;
		matchId = matchId.substring(0, 8);
		log.info(matchName);
		log.info(matchId);
		log.info(matchUrl);
		log.info(matchGroup);
		todo = matchTodo.replaceAll("\\r\\n","<br>").replaceAll(" ","&nbsp;").replaceAll("\"","\'");
		log.info(todo);
		log.info("length: "+todo.length());
		if(matchManagerImpl.queryMatch(matchId)!=null){
			model.put("msg", "比赛信息添加失败！");
			return "resultAdmin";
		}
		if(!matchManagerImpl.addMatch(matchId, matchName, todo, matchUrl, matchGroup)){
			model.put("msg", "比赛信息添加失败！");
			return "resultAdmin";
		}
		Match[] matchs = matchManagerImpl.queryAll();
		model.put("matchs", matchs);
		return "matchManager";
	}
	
	@RequestMapping(value="/matchDel", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String telDel(
			final @RequestParam(value = "matchId", required = true) String matchId,
			final ModelMap model){
		log.info(matchId);
		Map<String,Object> map=new HashMap<String, Object>();
		if(matchManagerImpl.queryMatch(matchId) == null){
			map.put("status", "0");
			map.put("msg", "删除失败！");
			log.info("删除失败！");
			return JSONObject.fromObject(map).toString();
		}
		if(matchManagerImpl.delMatch(matchId)){
			map.put("status", "1");
			map.put("msg", "删除成功！");
			log.info("删除成功！");
		}else{
			map.put("status", "0");
			map.put("msg", "删除失败！");
			log.info("删除失败！");
		}
		return JSONObject.fromObject(map).toString();
	}

}
