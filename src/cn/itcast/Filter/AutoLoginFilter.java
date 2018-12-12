package cn.itcast.Filter;

import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import cn.itcast.utils.MD5Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/25
 * Time:13:05
 */

@WebFilter("/*")
public class AutoLoginFilter implements Filter {

    private UserService userService = new UserServiceImpl();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 对请求和响应进行强制转换
        HttpServletRequest request  = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if (request.getSession().getAttribute("user") != null) {
            filterChain.doFilter(request,response);
        }else{
            String username = "";
            String password = "";

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("Cookie_username".equals(cookie.getName())) {
                        username =cookie.getValue();
                    }
                    if ("Cookie_password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }

            // 进行登陆
            if (!"".equals(username) && !"".equals(password)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(MD5Utils.md5(password));
                User login = userService.login(user);
                if (login != null) {
                    request.getSession().setAttribute("user",login.getName());
                    filterChain.doFilter(request,response);
                }
            }

            filterChain.doFilter(request,response);
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
