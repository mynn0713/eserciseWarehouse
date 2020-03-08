package com.lagou.blog.services;

import com.lagou.blog.pojo.Article;

import java.util.List;

public interface IHomePageService {
    List<Article> findAllArticles();
}
