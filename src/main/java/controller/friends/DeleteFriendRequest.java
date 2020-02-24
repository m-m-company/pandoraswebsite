package controller.friends;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteFriendRequest", name = "deleteFriendRequest")
public class DeleteFriendRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int friendID = Integer.parseInt(req.getParameter("idFriend"));
        int userID = ((User) req.getSession().getAttribute("user")).getId();
        if(DAOFactory.getInstance().makeUserDAO().deleteFriendRequest(userID, friendID)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
