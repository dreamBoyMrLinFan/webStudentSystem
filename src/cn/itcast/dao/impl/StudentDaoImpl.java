package cn.itcast.dao.impl;

import cn.itcast.dao.StudentDao;
import cn.itcast.pojo.Student;
import cn.itcast.utils.DrurdUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:12:52
 */
public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DrurdUtils.getDataSource());

    @Override
    public List<Student> findStudentAndPageOrName(Integer size, Integer pageIndex, String name) {
        if (name != null) {
            String sql = "select * from student where name like ? limit ?,?";
            List<Student> stulist = jdbcTemplate.query(sql, new RowMapper<Student>() {
                @Override
                public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                    int stuNo = resultSet.getInt("id");
                    String name1 = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    int sex = resultSet.getInt("sex");
                    Student student = new Student(stuNo,name1,age,sex);
                    return student;
                }
            }, "%" + name + "%", pageIndex, size);
            return stulist;
        } else {
            String sql = "select * from student limit ?,?";
            List<Student> stulist = jdbcTemplate.query(sql, new RowMapper<Student>() {
                @Override
                public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                    int stuNo = resultSet.getInt("id");
                    String name1 = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    int sex = resultSet.getInt("sex");
                    Student student = new Student(stuNo,name1,age,sex);
                    return student;
                }
            }, pageIndex, size);
            return stulist;
        }




    }

    /*查询总记录数*/
    @Override
    public Integer findStudentByCount() {
        String sql = "select count(*) from student";
        Integer pageCount = jdbcTemplate.queryForObject(sql, Integer.class);
        return pageCount;
    }

    @Override
    public Integer findStudentByCount(String name) {
        String sql = "select count(*) from student where name = ?";
        Integer pageCount = jdbcTemplate.queryForObject(sql, Integer.class,name);
        return pageCount;
    }

    @Override
    public void delStudentById(Integer stuNo) {
        String sql = "delete from student where id=?";
        jdbcTemplate.update(sql,stuNo);
    }

    @Override
    public Student findStudentById(Integer stuNo) {
        String sql  = "select * from student where id = ?";
        try {
            Student student = jdbcTemplate.queryForObject(sql, new RowMapper<Student>() {
                @Override
                public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                    int stuNo = resultSet.getInt("id");
                    String name1 = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    int sex = resultSet.getInt("sex");
                    Student student = new Student(stuNo, name1, age, sex);
                    return student;
                }
            }, stuNo);

            return student;
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public void insertStudent(Student student) {
        String sql = "insert into student(id,name,age,sex) values(?,?,?,?)";
        jdbcTemplate.update(sql,student.getStuNo(),student.getName(),student.getAge(),student.getSex());
    }

    @Override
    public void updateStudentById(Student student) {
        String sql = "update student set name=?,age=?,sex=? where id = ?";
        jdbcTemplate.update(sql,student.getName(),student.getAge(),student.getSex(),student.getStuNo());
    }

    @Override
    public List<Student> findStudentAll() {
        String sql = "select * from student";
        List<Student> stuList = jdbcTemplate.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                int stuNo = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int sex = resultSet.getInt("sex");
                Student student = new Student(stuNo, name1, age, sex);
                return student;
            }
        });
        return stuList;
    }
}
