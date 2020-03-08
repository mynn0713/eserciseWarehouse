package com.lagou.blog.repository;

import com.lagou.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomePageRepository extends JpaRepository<Article,Long> {
}
