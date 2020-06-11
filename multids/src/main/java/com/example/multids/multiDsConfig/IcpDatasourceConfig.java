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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.example.multids.mapper.icp",sqlSessionTemplateRef = "icpSqlSessionTemplate")
public class IcpDatasourceConfig {
    @Bean("icp")
    @ConfigurationProperties(prefix = "spring.datasource.icp")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("icpSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("icp") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/config/icp/*.xml"));
        return bean.getObject();
    }

    @Bean("icpSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("icpSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("icpTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("icp") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
