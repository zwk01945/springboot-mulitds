package com.example.multiaop.aop.multids;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    public static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);
    public static final String DEFAULT_DS="test";
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType (String dbType) {
        logger.info("切换数据库:" + dbType);
        contextHolder.set(dbType);
    }
    public static String getDbType () {
        return (String) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
