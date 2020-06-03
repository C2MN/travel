package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean registr(User user) {
        //1.根据用户名查询用户对象，如果用户对象存在返回false
        User user1 = userDao.findByUsername(user.getUsername());
        //判断user1是否为null
        if (user1 != null){
            //该用户信息已经注册过了
            return false;
        }
        //2.保存用户信息
        //2.1设置用户激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");

        userDao.save(user);

        //3.发送激活邮件"邮件正文"
        String content = "<a href ='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【旅游网】</a>";
        MailUtils.sendMail("13935717458@163.com",content,"旅游网激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
