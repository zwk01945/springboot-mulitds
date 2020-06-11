package com.example.multiaop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 从yml配置
 */
@Repository
public interface ObjectMapper {
    @Select("select * from TEST_CODE")
    List<Object> selectRecords();

    @Insert("insert into TEST_CODE (id,name) values (3,'aa'),(4,'dd')")
    int insertRecord();
}
