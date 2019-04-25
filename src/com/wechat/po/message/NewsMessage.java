package com.wechat.po.message;

import java.util.List;

import com.wechat.po.News;

/**
 * 图文消息实体类
 * @author chenkuan
 *
 */
public class NewsMessage extends BaseMessage {
	private String MsgType = "news";
	private int ArticleCount;
	private List<News> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	public String getMsgType() {
		return MsgType;
	}
	
	
}
