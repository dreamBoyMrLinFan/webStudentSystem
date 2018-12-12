package cn.itcast.serlvet;

import cn.itcast.pojo.PageBean;
import cn.itcast.pojo.Student;
import cn.itcast.service.StudentService;
import cn.itcast.service.impl.StudentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:12:43
 */
@WebServlet("/studentList")
public class StudentListServlet extends HttpServlet {

    private Integer PAGESIZE = 10;

    private StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
        } else {
            String method = req.getParameter("method");
            if ("".equals(method) || method == null) {
                studentList(req, resp);
            } else if ("delStudent".equals(method)) {
                this.delStudent(req,resp);
            }else if("findStudentById".equals(method)) {
                findStudentById(req, resp);
            } else if ("insertStudent".equals(method)) {
                try {
                    insertStudent(req,resp);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if ("updateStudent".equals(method)) {
                updateStudent(req,resp);
            } else if ("updateStudentById".equals(method)) {
                try {
                    updateStudentById(req,resp);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateStudentById(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException, ServletException {
        Map<String, String[]> map = req.getParameterMap();
        Student student = new Student();
        BeanUtils.populate(student,map);
        studentService.updateStudentById(student);
        studentList(req,resp);
    }


    /*编译学生信息之前要先准备数据 根据id去查询学生信息*/
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Student studentById = studentService.findStudentById(Integer.parseInt(id));
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(studentById);
        resp.getWriter().write(s);
    }

    /*添加学生*/
    private void insertStudent(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        Student student =new Student();
        BeanUtils.populate(student, parameterMap);
        studentService.insertStudent(student);
        studentList(req,resp);
    }


    /*查询当前学生学号是否被占用*/
    private void findStudentById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null && !"".equals(id)) {
            Student studentById = studentService.findStudentById(Integer.parseInt(id));
            ObjectMapper objectMapper = new ObjectMapper();
            if (studentById != null) {
                String s = objectMapper.writeValueAsString("当前学生学号已被占用");// writeValueAsString
                resp.getWriter().write(s);
            } else {
                String s = objectMapper.writeValueAsString("ok");
                resp.getWriter().write(s);
            }
        } else {
            resp.getWriter().write("当前学生学号不能为空");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /*跳转到学生信息页面并先准备数据*/
    private void studentList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("login");
        } else {
            String name = req.getParameter("nameSo");
            String pageNumber = req.getParameter("pageNumber");
            // 对传过来的要请求的页数进行处理 以防止解析错误
            int pageNum = 1;
            if (pageNumber != null) {
                pageNum = Integer.parseInt(pageNumber);
                if (pageNum < 0) {
                    pageNum = 1;
                }
            }
            PageBean<Student> pageStudent = studentService.findStudentAndPageOrName(pageNum, PAGESIZE, name);
            req.setAttribute("findName",name);
            req.getSession().setAttribute("pageStudent", pageStudent);
            req.getRequestDispatcher("WEB-INF/Student_list.jsp").forward(req,resp);

        }

    }


    private void delStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            String[] ids = id.split("-");
            if (ids != null) {
                studentService.delStduent(ids);
                resp.sendRedirect("studentList");
            }
        }

    }

}
