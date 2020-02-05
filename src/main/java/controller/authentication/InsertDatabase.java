package controller.authentication;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/register/insertDatabase", name = "insertUserToDatabase")
public class InsertDatabase extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        DAOFactory.getInstance().makeUserDAO().insertUser(email, username, password, "Ciao, sono " + username, false);
        resp.sendRedirect("/?registered=true");
    }

}
