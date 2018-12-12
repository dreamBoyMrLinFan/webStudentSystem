package cn.itcast.serlvet.login;

import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import cn.itcast.utils.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.CollationKey;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:1:30
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("".equals(method) || method == null) {
            this.doLoginUI(req, resp);
        } else if ("login".equals(method)) {
            this.login(req, resp);
        } else if ("isCode".equals(method)) {
            this.isCode(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private void isCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code"); // 从用户中获取到输入的验证码
        String FuCode = (String) req.getSession().getAttribute("code_session"); // 从服务器中获取到验证码
        if (!code.equalsIgnoreCase(FuCode)) {
            resp.getWriter().write("false");
        } else {
            resp.getWriter().write("true");
        }
    }

    /*验证登陆*/
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String autoLogin = req.getParameter("autoLogin"); // 查看用户是否勾选自动登陆
        User user = new User();
        if (username != null && password != null) {
            user.setUsername(username);
            user.setPassword(MD5Utils.md5(password)); // 使用md5加密
            User login = userService.login(user);
            if (login != null) {
                req.getSession().setAttribute("user", login.getName()); // 把用户名存放在session中方便首页进行展示
                if ("yes".equals(autoLogin)) {
                    Cookie cookie_username = new Cookie("Cookie_username", username);
                    Cookie cookie_password = new Cookie("Cookie_password", password);
                    cookie_username.setMaxAge(60 * 10 * 10 * 24 * 7); // cookie存放的时间为一周
                    cookie_password.setMaxAge(60 * 10 * 10 * 24 * 7);
                    cookie_username.setPath(req.getContextPath());
                    cookie_password.setPath(req.getContextPath());
                    resp.addCookie(cookie_username); // 把cookie响应回去
                    resp.addCookie(cookie_password);
                }

                resp.sendRedirect("index");
            } else {
                req.setAttribute("errorLogin", "用户名或密码错误");
                req.setAttribute("username", username);
                req.setAttribute("password", password);
                req.setAttribute("code", req.getParameter("code"));
                this.doLoginUI(req, resp);
            }
        }

    }

    /*跳转到登陆页面的方法*/
    private void doLoginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("index");
        }

    }

}
