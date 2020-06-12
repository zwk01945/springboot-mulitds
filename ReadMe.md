#########################
文档说明
1. 数据库环境为达梦，可自行配置所需数据库的驱动
2. springboot + mybatis + dynamic 实现多数据源
3. 使用DS注解和配置文件application.yml配置
4. 注解可添加到Service或者Mapper上，不建议同时添加，会出问题，
   也可以在当前Mapper下使用别的库，只需要使用@DS标注注明数据库即可
##########################
DS注解源码分析
1. 项目启动会加载DynamicDataSourceAutoConfiguration，配置文件中注入了DynamicRoutingDatasource，并且调用了
DynamicRoutingDatasouce的init方法初始化连接，将数据源放入到DynamicRoutingDatasouce的dataSourceMap中
2. 发起请求的时候，DynamicDataSourceAnnotationInterceptor拦截到添加了@DS注解的方法，将@DS注解的value存放到DynamicDataSourceContextHolder的LOOKUP_KEY_HOLDER中
3. 当请求获取数据库连接时，AbstractRoutingDataSource的getConnection()方法，从LOOKUP_KEY_HOLDER中取到当前配置的数据源
   名称，然后从dataSourceMap中获取到DataSource对象，然后dataSource.getConnection()获取数据库连接

################################
功能说明
1. 使用注解切换数据源，可以设置默认数据源
2. 注解会有默认值，会默认找到方法的注解，如果类和方法都标注，会优先使用方法上的的注解内容
3. 多数据源下支持事务，也支持切换事务下多数据源切换
