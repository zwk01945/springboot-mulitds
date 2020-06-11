package com.example.multiaop.service;

import com.example.multiaop.aop.DS;
import com.example.multiaop.mapper.IcpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("icpService")
@DS("icp")
public class IcpService {

    @Autowired
    IcpMapper icpMapper;

    @Transactional(value = "transactionManager",rollbackFor = Exception.class)
    public int insertIcp() {
        int insert = icpMapper.insert();
//        int i = 1/0;
        return insert ;
    }

}
