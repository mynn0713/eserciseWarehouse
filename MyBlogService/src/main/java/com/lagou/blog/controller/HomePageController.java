package com.lagou.blog.controller;

import com.lagou.blog.pojo.Article;
import com.lagou.blog.services.IHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/homepage")
public class HomePageController {

    @Autowired
    IHomePageService homePageService;

    @RequestMapping("/showArticle")
    public String showBlogToHomePage(Model model){
        List<Article> allArticles = homePageService.findAllArticles();
        model.addAttribute("allArticles",allArticles);

        return "client/index";
    }
}
