package com.weixin.server.message.resp;

import java.util.List;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: NewsMessage
 * @Description: TODO(图文消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:50:50
 */
public class RespNewsMessage extends BaseMessage{
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public RespNewsMessage(String fromUserName,String toUserName){
		super(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	}
	
	public RespNewsMessage(){
		
	}
	
	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
