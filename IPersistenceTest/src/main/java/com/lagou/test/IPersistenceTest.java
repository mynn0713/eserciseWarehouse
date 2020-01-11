package com.lagou.test;

import com.lagou.dao.IUserMapper;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {
    @Test
    public void test() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().builder(resourceAsStream);
        SqlSession sqlSession = builder.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        IUserMapper mapper = sqlSession.getMappper(IUserMapper.class);
        List<User> users = mapper.selectList();
        //List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1.getId()+":"+user1.getUsername());
        }
    }

    @Test
    public void test2() throws Exception {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().builder(resourceAsStream);
        SqlSession sqlSession = builder.openSession();
        User user = new User();
        user.setId(999);
        user.setUsername("张三999");
        IUserMapper mapper = sqlSession.getMappper(IUserMapper.class);
        int successNum = mapper.insertObj(user);
        System.out.println(successNum);

        List<User> users = mapper.selectList();
        //List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1.getId()+":"+user1.getUsername());
        }
    }
}
