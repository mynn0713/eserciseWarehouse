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
        IUserMapper mapper = sqlSession.getMappper(IUserMapper.class);
        //新增
        /*User user = new User();
        user.setId(998);
        user.setUsername("张三998");
        int successInsertNum = mapper.insertObj(user);
        System.out.println(successInsertNum);*/

        List<User> users = mapper.selectList();
        //List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1.getId()+":"+user1.getUsername());
        }

        //修改
        User user2 = new User();
        user2.setId(999);
        user2.setUsername("张三666");
        int successUpdateNum = mapper.updateById(user2);
        System.out.println(successUpdateNum);

        List<User> users2 = mapper.selectList();
        //List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users2) {
            System.out.println(user1.getId()+":"+user1.getUsername());
        }

        //删除
        User user3 = new User();
        user3.setId(999);
        int successDelNum = mapper.deleteById(user3);
        System.out.println(successDelNum);

        List<User> users3 = mapper.selectList();
        //List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users3) {
            System.out.println(user1.getId()+":"+user1.getUsername());
        }
    }
}
