package cn.itcast.serlvet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IDEA
 * author:LinFan
 * Date:2018/10/23
 * Time:17:36
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("login");
        } else {
            String method = req.getParameter("method");
            if ("".equals(method) || method == null) {
               req.getRequestDispatcher("WEB-INF/student_index.jsp").forward(req,resp);
            } else if (method.equals("indexUI")) {
                IndexUI(req,resp);
            }
        }

    }

    private void IndexUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("login");
        } else {
            req.getRequestDispatcher("WEB-INF/index.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
