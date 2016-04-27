package com.weixin.server.message.resp;

import java.util.List;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: NewsMessage
 * @Description: TODO(ͼ����Ϣ) 
 * @author: zhanggd
 * @date 2016��3��3������6:50:50
 */
public class RespNewsMessage extends BaseMessage{
	// ͼ����Ϣ����������Ϊ10������
	private int ArticleCount;
	// ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
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
