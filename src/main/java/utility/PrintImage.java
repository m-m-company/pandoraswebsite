package utility;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/printImage")
public class PrintImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u;
        if(req.getParameter("id") != null){
            int userId = Integer.parseInt(req.getParameter("id"));
            u = DAOFactory.getInstance().makeUserDAO().getUserById(userId);
        }
        else {
            u = (User) req.getSession().getAttribute("user");
        }
        byte[] imageBytes = DAOFactory.getInstance().makeUserDAO().getProfilePicture(u.getId());
        resp.setContentType("image/jpeg");
        resp.setContentLength(imageBytes.length);
        resp.getOutputStream().write(imageBytes);
    }

}
