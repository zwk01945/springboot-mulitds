package com.example.multiaop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 主库测试
 */
@SpringBootTest(classes = MultiaopApplication.class)
@RunWith(SpringRunner.class)
public class TestService {

    @Autowired
    com.example.multiaop.service.TestService testService;

    /**
     * 测试一个方法内部调用多库出异常的事务
     */
    @Test
    public void testNontrs() {
        int i = testService.insertAll();
        System.out.println(i);
    }

    /**
     * 测试单库插入数据出异常的事务
     */
    @Test
    public void testOne(){
        int i = testService.insertObject();
        System.out.println(i);
    }

    /**
     * 测试不标注DS情况下，默认数据源是否生效
     */
    @Test
    public void testDefault(){
        int i = testService.insertDefault();
        System.out.println(i);
    }

    /**
     * 测试只标注，但是不写值
     */
    @Test
    public void testDefaultAnnotation(){
        int i = testService.insertDefaultAnnotation();
        System.out.println(i);
    }

}
