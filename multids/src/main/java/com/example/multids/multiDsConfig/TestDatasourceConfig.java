package com.example.multids.multiDsConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.multids.mapper.test", sqlSessionTemplateRef = "testSqlSessionTemplate")
public class TestDatasourceConfig {


    /**
     * 配置数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.test")
    @Bean("test")
    @Primary
    public DataSource dataSourceConfig() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置数据源所需sqlsessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean("testSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/config/test/*.xml"));
        return bean.getObject();
    }

    /**
     * 将sqlsession注入到sqlsessionTemplate中
     * @param sqlSessionFactory
     * @return
     */
    @Bean("testSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("testTransactionManager")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("test") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
