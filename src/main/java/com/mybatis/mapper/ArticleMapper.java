package com.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ArticleMapper {

    @Select("select * from article")
    List<Map<String, Object>> getList();

}
