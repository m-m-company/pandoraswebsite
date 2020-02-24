package controller.friends;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteFriend", name = "deleteFriend")
public class DeleteFriend extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idFriend = Integer.parseInt(req.getParameter("idFriend"));
        int idActual = ((User) req.getSession().getAttribute("user")).getId();
        if(DAOFactory.getInstance().makeUserDAO().deleteFriend(idActual, idFriend)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(401);
        }
    }

}
