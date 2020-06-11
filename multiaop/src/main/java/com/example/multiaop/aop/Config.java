package com.example.multiaop.aop;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.example.multiaop.aop.dbconfig.DbConfigOne;
import com.example.multiaop.aop.dbconfig.DbConfigTwo;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    @Autowired
    DbConfigOne dbConfigOne;
    @Autowired
    DbConfigTwo dbConfigTwo;

    @Bean("test")
    public DataSource testdataSource(DbConfigOne dbConfigOne) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setURL(dbConfigOne.getJdbcUrl());
        mysqlXaDataSource.setUser(dbConfigOne.getUsername());
        mysqlXaDataSource.setPassword(dbConfigOne.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("test");
        return xaDataSource;
    }

    //
    @Bean("icp")
    public DataSource icpdataSource(DbConfigTwo dbConfigTwo) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setURL(dbConfigTwo.getJdbcUrl());
        mysqlXaDataSource.setUser(dbConfigTwo.getUsername());
        mysqlXaDataSource.setPassword(dbConfigTwo.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("icp");
        return xaDataSource;
    }


//    @Bean("transactionManager")
//    public PlatformTransactionManager testTransactionManager(@Qualifier("dynamicDataSource") DataSource testDatasource){
//        return new DataSourceTransactionManager(testDatasource);
//    }

    @Bean(name = "atomikosTransactionManager")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager atomikosTransactionManager = new UserTransactionManager();
        atomikosTransactionManager.setForceShutdown(true);
        return atomikosTransactionManager;
    }

    @Bean(name = "atomikosUserTransaction")
    public UserTransactionImp atomikosUserTransaction() {
        UserTransactionImp atomikosUserTransaction = new UserTransactionImp();
        try {
            atomikosUserTransaction.setTransactionTimeout(300);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return atomikosUserTransaction;
    }

    @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager(UserTransactionManager atomikosTransactionManager,
                                                    UserTransactionImp atomikosUserTransaction) {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionManager(atomikosTransactionManager);
        transactionManager.setUserTransaction(atomikosUserTransaction);
        transactionManager.setAllowCustomIsolationLevels(true);
        return transactionManager;
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 指定扫描的xml文件所在位置，在配置文件里面配置，会报Invalid bound statement
//        Resource[] resources = new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mybatis/*.xml");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
//        bean.setMapperLocations(resources);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() throws SQLException {
        DynamicDatasource dynamicDataSource = new DynamicDatasource();
        dynamicDataSource.setDefaultTargetDataSource(testdataSource(dbConfigOne));
        Map<Object, Object> dbMap = new HashMap<Object, Object>();
        dbMap.put("test", testdataSource(dbConfigOne));
        dbMap.put("icp", icpdataSource(dbConfigTwo));
        dynamicDataSource.setTargetDataSources(dbMap);

        return dynamicDataSource;
    }

}
