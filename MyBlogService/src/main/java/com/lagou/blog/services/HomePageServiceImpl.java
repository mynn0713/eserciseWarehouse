package com.lagou.blog.services;

import com.lagou.blog.pojo.Article;
import com.lagou.blog.repository.HomePageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HomePageServiceImpl implements IHomePageService{
    @Autowired
    HomePageRepository homePageRepository;
    @Override
    public List<Article> findAllArticles() {
        List<Article> allArticle = homePageRepository.findAll();
        return allArticle;
    }
}
