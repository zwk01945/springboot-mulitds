package com.example.multiaop.service;

import com.example.multiaop.aop.multids.DS;
import com.example.multiaop.mapper.IcpMapper;
import com.example.multiaop.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("icpService")
@DS("icp")
public class IcpService {

    @Autowired
    IcpMapper icpMapper;
    @Autowired
    ObjectMapper objectMapper;


    public int insertIcp() {
        int insert = icpMapper.insert();
        return insert ;
    }


    @DS("test")
    public int insertTest() {
        int insert = objectMapper.insertRecord();
        return insert ;
    }

    @DS("test")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertTest1() {
        int insert = objectMapper.insertRecord();
        return insert ;
    }

    @DS("test")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertTest2() {
        int insert = objectMapper.insertRecord();
        int i = 1 / 0;
        return insert ;
    }


}
