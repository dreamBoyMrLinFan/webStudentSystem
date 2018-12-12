package cn.itcast.service;

import cn.itcast.pojo.PageBean;
import cn.itcast.pojo.Student;

import java.util.List;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:12:45
 */
public interface StudentService {

    /*分页加条件查询学生信息*/
    public PageBean<Student> findStudentAndPageOrName(Integer pageNumber, Integer pageSize, String name);

    /*根据id删除学生信息*/
    public void delStduent(String ... id);


    /*根据学生id查找学生信息*/
    public Student findStudentById(Integer stuNo);

    /*添加学生*/
    public void insertStudent(Student student);

    /*根据id修改学生信息*/
    public void updateStudentById(Student student);


    public List<Student> findStudentAll();




}
