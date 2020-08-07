package com.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface UserMapper {

    @Select("select * from sys_user")
    List<Map<String, Object>> getList();

}
