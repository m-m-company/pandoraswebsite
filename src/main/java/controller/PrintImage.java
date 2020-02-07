package controller;

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
        else{
            u = (User) req.getSession().getAttribute("user");
        }
        if(u.isGoogleUser()){
            String urlImage = DAOFactory.getInstance().makeUserDAO().getGoogleProfileImage(u.getEmail());
            this.log(urlImage);
            resp.setContentType("text/html");
            resp.getWriter().write(urlImage);
        }
        else {
            byte[] imageBytes = u.getImage();
            resp.setContentType("image/jpeg");
            resp.setContentLength(imageBytes.length);
            resp.getOutputStream().write(imageBytes);
        }
    }

}