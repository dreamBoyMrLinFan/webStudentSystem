package cn.itcast.dao;

import cn.itcast.pojo.Student;

import java.util.List;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:12:51
 */
public interface StudentDao {

    // 分页查询
    public List<Student> findStudentAndPageOrName(Integer size,Integer pageIndex,String name);

    // 查询总记录数
    public Integer findStudentByCount();
    // 带条件的查询
    public Integer findStudentByCount(String name);

    /*根据id删除学生信息*/
    public void delStudentById(Integer stuNo);

    /* 根据学生id查询学生信息 */
    public Student findStudentById(Integer stuNo);

    /*添加学生*/
    public void insertStudent(Student student);


    public void updateStudentById(Student student);


    public List<Student> findStudentAll();
}
