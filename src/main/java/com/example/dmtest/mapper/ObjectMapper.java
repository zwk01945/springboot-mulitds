package com.example.dmtest.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 从yml配置
 */
@DS("test")
@Repository
public interface ObjectMapper {
    @Select("select * from IP_BA_BM.IP_RECORD_DISTRIBUTE_T")
    List<Object> selectRecords();


    @Select("select * from  IP_BA_BM.IP_CODE_UNIT_NATURE_T")
    @DS("icp")
    List<Object> selectAll();

}
