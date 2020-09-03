package com.mybatis.service;

import com.mybatis.mapper.UserMapper;
import com.spring.transction.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void getList() {
        userMapper.getList();
    }

    public void saveUser() {
        TransactionUtil.execute("INSERT INTO user(`name`, `age`) VALUES (?, ?)", "ac", "20");
//        TransactionUtil.execute("UPDATE user t SET t.age = t.age - 1 WHERE t.id = ?", "4");
    }

}
