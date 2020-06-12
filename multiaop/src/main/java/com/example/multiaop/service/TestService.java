package com.example.multiaop.service;

import com.example.multiaop.aop.multids.DS;
import com.example.multiaop.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("testService")
public class TestService {

    //本TEST库的服务dao
    @Autowired
    ObjectMapper objectMapper;

    //ICP库的服务方法
    @Autowired
    IcpService icpService;


    public String selectAll() {
        List<Object> objects = objectMapper.selectRecords();
        return objects.toString();
    }

    /**
     * 当前业务中用到ICP方法,开启事务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @DS(value = "test")
    public int insertAll() {
        int i = 0;
        i+= objectMapper.insertRecord();
        i+= icpService.insertIcp();
        int m = 1/0;
        return i;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @DS(value = "test")
    public int insertObject(){
        int i = objectMapper.insertRecord();
        int m = 1 / 0;
        return i;
    }

    public int insertDefault() {
        int i = objectMapper.insertRecord();
        return i;
    }
    @DS
    public int insertDefaultAnnotation(){
        int i = objectMapper.insertRecord();
        return i;
    }

}
