package com.example.multids;

import com.example.multids.mapper.icp.IcpMapper;
import com.example.multids.mapper.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class ApiController {

    @Autowired
    TestMapper testMapper;
    @Autowired
    IcpMapper icpMapper;


    @RequestMapping("/t")
    public String gettest(){
        return testMapper.selectAll().toString();
    }

    @RequestMapping("/i")
    public String geti(){
        return icpMapper.selectAll().toString();
    }

}
