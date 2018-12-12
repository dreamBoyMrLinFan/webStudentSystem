package cn.itcast.service;

import cn.itcast.pojo.User; /**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/24
 * Time:17:37
 */
public interface UserService {
    public String isUserName(String name);

    public void insertUser(User user);

    public User login(User user);
}
