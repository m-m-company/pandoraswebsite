package controller.friends;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/refuseFriendRequest", name = "refuseFriendRequest")
public class RefuseFriendRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actualId = ((User) req.getSession().getAttribute("user")).getId();
        int friendId = Integer.parseInt(req.getParameter("idUser"));
        if(DAOFactory.getInstance().makeUserDAO().refuseFriendRequest(friendId, actualId)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
