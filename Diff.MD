**三种不同方式的大致区别**

1. 使用方式上
   
   最外层的基于框架封装只需要在配置文件中添加数据源信息，在代码中使用@DS("xx")指定
   数据源既可.
   
   multids使用的是给具体Mapper配置自己的Datasource和sqlSessionFactory和sqlSessionTemplate以及事务管理
   不需要使用注解去标注就已经全部配置好，过程比较繁琐。
   
   multiaop是使用自定义注解@DS和Aop去拦截请求的方式，动态拿到注解中的value去切换数据源

2. 问题
  
   多数据源下事务只支持单个数据源的事务，因为在切换数据源后开启事务，事务中会缓存连接信息，所以之后的
   去修改线程中的变量值没有实际改变数据源，导致在一个方法中去处理多库的service的时候
   会发现数据源切换虽然显示成功，却没有真正的切换，抛表不存在异常
   
   该问题将使用分布式事务去解决，有兴趣的可自行了解
   