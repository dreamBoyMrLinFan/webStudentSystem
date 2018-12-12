package cn.itcast.serlvet.register;

import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import cn.itcast.utils.MD5Utils;
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
 * Date:2018/10/24
 * Time:11:37
 */
@WebServlet("/register")
public class RegisterServlet extends  HttpServlet{

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method"); // 获取指定名称的内容
        if ("isUserName".equals(method)) {
            this.isUserName(req,resp);
        } else if ("register".equals(method)) {
            try {
                this.register(req,resp);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if("".equals(method) || method == null) {
            this.doRegisterUI(req,resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    /*注册的方法*/
    private void register(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        BeanUtils.populate(user,parameterMap);

        if (user != null) {
            user.setPassword(MD5Utils.md5(user.getPassword())); // 对密码进行md5加密
            userService.insertUser(user); // 注册用户
            resp.sendRedirect("login");
        }



    }

    /*判断用户名是否存在*/
    private void isUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); // 获取前端传过来的用户输入要注册的用户名，然后去数据库查询是否存在
        if (username != null ) {
            String boo = userService.isUserName(username);
            resp.getWriter().write(boo);
        }
    }



//    跳转到登陆页面的servlet方法
    private void doRegisterUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/register.jsp").forward(req,resp);
    }
}
