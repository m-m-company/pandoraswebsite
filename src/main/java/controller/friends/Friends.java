package controller.friends;

import com.google.gson.Gson;
import model.User;
import persistence.DAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(value = "/friends", name = "friends")
public class Friends extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("header.jsp");
        rd.include(req, resp);
        if ((Boolean)req.getSession().getAttribute("logged") == null || !(Boolean)req.getSession().getAttribute("logged")) {
            rd = req.getRequestDispatcher("errorNotLogged.html");
            rd.include(req,resp);
            rd = req.getRequestDispatcher("footer.html");
            rd.include(req, resp);
            return;
        }
        rd = req.getRequestDispatcher("friendMenu.html");
        rd.include(req, resp);
        rd = req.getRequestDispatcher("friends.html");
        rd.include(req, resp);
        rd = req.getRequestDispatcher("footer.html");
        rd.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        int id = ((User) req.getSession().getAttribute("user")).getId();
        ArrayList<User> users = DAOFactory.getInstance().makeUserDAO().getUsersByUsername(username, id);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);
    }

}
