package com.example.multiaop.service;

import com.example.multiaop.aop.DS;
import com.example.multiaop.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Service("testService")
@DS(value = "test")
public class testService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IcpService icpService;

    @Autowired
    private JtaTransactionManager transactionManager;


    public String selectAll() {
        List<Object> objects = objectMapper.selectRecords();
        return objects.toString();
    }

    public int insertAll() {
        UserTransaction userTransaction = transactionManager.getUserTransaction();
        int i = 0;
        try {
            userTransaction.begin();
            i+= objectMapper.insertRecord();
            int m = 1/0;
            i+= icpService.insertIcp();
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            e.printStackTrace();
        }

        return i;
    }


}
