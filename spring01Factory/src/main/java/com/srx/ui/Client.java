package com.srx.ui;

import com.srx.factory.BeanFactory;
import com.srx.service.IAccountService;
import com.srx.service.impl.AccountServiceImpl;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {
    public static void main(String[] args) {
        IAccountService service = (IAccountService) BeanFactory.getBean("accountService");
        service.saveAccount();
    }
}
