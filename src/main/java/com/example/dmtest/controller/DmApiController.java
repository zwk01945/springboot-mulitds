package com.example.dmtest.controller;

import com.example.dmtest.mapper.IcpMapper;
import com.example.dmtest.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DmApiController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IcpMapper icpMapper;

    /**
     * 测试主数据库
     * @return
     */
    @RequestMapping("/t")
    public String getT() {
        Object o = objectMapper.selectRecords();
        return o.toString();
    }

    /**
     * 测试从库
     * @return
     */
    @RequestMapping("/i")
    public String getI() {
        Object o = icpMapper.selectAll();
        return o.toString();
    }

    /**
     * 测试在主库mapper下查从库
     * @return
     */
    @RequestMapping("/t/i")
    public String getTi() {
        Object o = objectMapper.selectAll();
        return o.toString();
    }


}
