package com.example.multiaop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 从库测试
 */
@SpringBootTest(classes = MultiaopApplication.class)
@RunWith(SpringRunner.class)
public class IcpService {

    @Autowired
    com.example.multiaop.service.IcpService icpService;

    /**
     * 测试单标注下数据源
     */
    @Test
    public void test() {
        int i = icpService.insertIcp();
        System.out.println(i);
    }

    /**
     * 测试使用双注解标注换库
     */
    @Test
    public void test1() {
        int i = icpService.insertTest();
        System.out.println(i);
    }

    /**
     * 测试在双标注下，事务是否会影响数据源切换
     */
    @Test
    public void test2() {
        int i = icpService.insertTest1();
        System.out.println(i);
    }

    /**
     * 测试在双标注下，事务是否会执行
     */
    @Test
    public void test3() {
        int i  = icpService.insertTest2();
        System.out.println(i);
    }


}
