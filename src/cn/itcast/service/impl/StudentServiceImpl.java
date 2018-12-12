package cn.itcast.service.impl;

import cn.itcast.dao.StudentDao;
import cn.itcast.dao.impl.StudentDaoImpl;
import cn.itcast.pojo.PageBean;
import cn.itcast.pojo.Student;
import cn.itcast.service.StudentService;

import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:12:50
 */
public class StudentServiceImpl implements StudentService{

    private StudentDao studentDao = new StudentDaoImpl();


    @Override
    public PageBean<Student> findStudentAndPageOrName(Integer pageNumber, Integer pageSize, String name) {
        PageBean<Student> pageBean = new PageBean<>();
        pageBean.setPageSize(pageSize);
        pageBean.setPageNumber(pageNumber);
        Integer pageCount = null ;
        if (name != null) {
            pageCount = studentDao.findStudentByCount(name);
        } else {
            pageCount = studentDao.findStudentByCount();
        }
        pageBean.setTotalRecord(pageCount);
        List<Student> studentList = studentDao.findStudentAndPageOrName(pageSize, pageBean.getStartIndex(), name);
        pageBean.setList(studentList);
        return pageBean;
    }

    @Override
    public void delStduent(String... id) {
            for (String s : id) {
                int stuId = Integer.parseInt(s);
                studentDao.delStudentById(stuId);
            }
    }

    @Override
    public Student findStudentById(Integer stuNo) {
       return studentDao.findStudentById(stuNo);
    }

    @Override
    public void insertStudent(Student student) {
        studentDao.insertStudent(student);
    }

    @Override
    public void updateStudentById(Student student) {
        studentDao.updateStudentById(student);
    }

    @Override
    public List<Student> findStudentAll() {
        return studentDao.findStudentAll();
    }



}
