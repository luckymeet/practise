package com.mybatis;

import com.mybatis.mapper.ArticleMapper;
import com.mybatis.mapper.UserMapper;
import com.mybatis.session.MySqlSession;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

//    private com.mysql.cj.jdbc.Driver driver;

    @Test
    public void test() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<Map<String, Object>> user = mapper.getList();
            System.out.println(user);
            session.clearCache();
        }
        MySqlSession<Object> mySqlSession = new MySqlSession<>();
        UserMapper userMapper = mySqlSession.getMapper(UserMapper.class);
        ArticleMapper articleMapper = mySqlSession.getMapper(ArticleMapper.class);
        userMapper.getList();
        articleMapper.getList();
    }

}
