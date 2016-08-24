package com.nmq.domain;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.List;

/**
 * 图文消息实体bean
 * Created by niemengquan on 2016/8/24.
 */
public class ImageAndTextMessage extends BaseMessage{
    private Integer ArticleCount;
    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    List<News> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
