package com.example.multiaop.controller;

import com.example.multiaop.service.IcpService;
import com.example.multiaop.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3")
public class ApiController {

    @Autowired
    TestService testService;
    @Autowired
    IcpService icpService;

    @RequestMapping("/t")
    public int insertTest() {
        int i = testService.insertAll();
        return i;
    }

    @RequestMapping("/i")
    public int insertIcp() {
        int i = icpService.insertIcp();
        return i;
    }
}
