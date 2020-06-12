package com.example.multiaop.aop;

import com.example.multiaop.aop.dbconfig.DbConfigOne;
import com.example.multiaop.aop.dbconfig.DbConfigTwo;
import com.example.multiaop.aop.multids.DynamicDatasource;
import com.example.multiaop.aop.transcation.MultiDataSourceTransactionFactory;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置文件
 * 包括SqlsessionFactory、SqlsessionTemplate、多数据源事务管理器atomik
*/
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class Config {

    @Autowired
    DbConfigOne dbConfigOne;
    @Autowired
    DbConfigTwo dbConfigTwo;

    /**
     * 第一个库数据源信息注入到Spring
     * @param dbConfigOne 与配置文件搭配第一个数据源信息
     * @return
     * @throws SQLException
     */
    @Bean("test")
    public DataSource testdataSource(DbConfigOne dbConfigOne) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setURL(dbConfigOne.getJdbcUrl());
        mysqlXaDataSource.setUser(dbConfigOne.getUsername());
        mysqlXaDataSource.setPassword(dbConfigOne.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(dbConfigOne.getDbName());
        return xaDataSource;
    }

    /**
     * 第二个库数据源信息注入到Spring
     * @param dbConfigTwo
     * @return
     * @throws SQLException
     */
    @Bean("icp")
    public DataSource icpdataSource(DbConfigTwo dbConfigTwo) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setURL(dbConfigTwo.getJdbcUrl());
        mysqlXaDataSource.setUser(dbConfigTwo.getUsername());
        mysqlXaDataSource.setPassword(dbConfigTwo.getPassword());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(dbConfigTwo.getDbName());
        return xaDataSource;
    }

    /**
     * 多数据源信息
     * @return
     * @throws SQLException
     */
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


//    @Bean("transactionManager")
//    public PlatformTransactionManager testTransactionManager(@Qualifier("dynamicDataSource") DataSource testDatasource){
//        return new DataSourceTransactionManager(testDatasource);
//    }

    /**
     * atomikos事务配置信息
     * @return
     */
//    @Bean(name = "atomikosTransactionManager")
//    public UserTransactionManager atomikosTransactionManager() {
//        UserTransactionManager atomikosTransactionManager = new UserTransactionManager();
//        atomikosTransactionManager.setForceShutdown(true);
//        return atomikosTransactionManager;
//    }
//
//    @Bean(name = "atomikosUserTransaction")
//    public UserTransactionImp atomikosUserTransaction() {
//        UserTransactionImp atomikosUserTransaction = new UserTransactionImp();
//        try {
//            atomikosUserTransaction.setTransactionTimeout(300);
//        } catch (SystemException e) {
//            e.printStackTrace();
//        }
//        return atomikosUserTransaction;
//    }
//
//    @Bean(name = "transactionManager")
//    public JtaTransactionManager transactionManager(UserTransactionManager atomikosTransactionManager,
//                                                    UserTransactionImp atomikosUserTransaction) {
//        JtaTransactionManager transactionManager = new JtaTransactionManager();
//        transactionManager.setTransactionManager(atomikosTransactionManager);
//        transactionManager.setUserTransaction(atomikosUserTransaction);
//        transactionManager.setAllowCustomIsolationLevels(true);
//        return transactionManager;
//    }


    /**
     * mybatis手动注入多数据源
     * 注释部分为XML的位置
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTransactionFactory(new MultiDataSourceTransactionFactory());
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


}
