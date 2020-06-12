############################
项目介绍
1. 使用Spring管理数据源，可以事先手动注入数据源交给Spring管理
2. 自定义注解，传入String并给定默认的值，即默认数据源名称
3. 使用SpringAop去切面类处理请求，指定只有被注解标注才会被拦截执行
4. 然后在切面类中获取切点的Clazz和method方法，参数列表，依次判断是否被注解标注，
   如果标注则从注解中取出数据源名称，切换到数据源。
#######################################
特别记录一下使用Atomikos的手动提交事务
config文件中注入如下的bean
    
    //使用方式
    
    @Autowired
    private JtaTransactionManager transactionManager;
    
    UserTransaction userTransaction = transactionManager.getUserTransaction();
            int i = 0;
            try {
                userTransaction.begin();
                i+= objectMapper.insertRecord();
                i+= icpService.insertIcp();
                userTransaction.commit();
                int m = 1/0;
            } catch (Exception e) {
                try {
                    userTransaction.rollback();
                } catch (SystemException systemException) {
                    systemException.printStackTrace();
                }
                e.printStackTrace();
            }
    
    
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

