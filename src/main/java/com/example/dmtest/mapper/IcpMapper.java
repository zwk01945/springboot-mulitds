package com.example.dmtest.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@DS("icp")
@Repository
public interface IcpMapper {

    @Select("select * from  IP_BA_BM.IP_CODE_UNIT_NATURE_T")
    List<Object> selectAll();

}
