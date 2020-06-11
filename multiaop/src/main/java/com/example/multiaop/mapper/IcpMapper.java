package com.example.multiaop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IcpMapper {

    @Select("select * from  ICP_CODE")
    List<Object> selectAll();
    @Insert("insert into ICP_CODE (id,name) values(3,'aaa'),(4,'asdad')")
    int insert();
}
