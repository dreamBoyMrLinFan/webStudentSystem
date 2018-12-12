package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.pojo.User;
import cn.itcast.utils.DrurdUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/24
 * Time:17:41
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DrurdUtils.getDataSource());

    @Override
    public String isUserName(String name) {
        String sql = "select * from user where username=?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), name);
            return "flase";
        }catch (Exception e){
            return "true";
        }
    }

    @Override
    public void insertUser(User user) {
        String sql = "insert into user(username,password,name) values(?,?,?)";
        int update = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName());
    }

    @Override
    public User login(User user) {
        String sql = "select username, password,NAME from user where username=? and password=?";
        try{
            User user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
            return user1;
        }catch (Exception e){
            return null;
        }

    }
}
