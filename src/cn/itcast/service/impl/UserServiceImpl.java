package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/24
 * Time:17:38
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public String isUserName(String name) {
        return userDao.isUserName(name);
    }

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }
}
