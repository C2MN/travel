package com.srx.service.impl;

import com.srx.dao.IAccountDao;
import com.srx.dao.impl.AccountDaoImpl;
import com.srx.factory.BeanFactory;
import com.srx.service.IAccountService;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    //private IAccountDao accountDao = new AccountDaoImpl();
    private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");
    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
