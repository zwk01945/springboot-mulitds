package com.example.multiaop.service;

import com.example.multiaop.aop.multids.DS;
import com.example.multiaop.mapper.IcpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("icpService")
@DS("icp")
public class IcpService {

    @Autowired
    IcpMapper icpMapper;

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public int insertIcp() {
        int insert = icpMapper.insert();
        return insert ;
    }

}
