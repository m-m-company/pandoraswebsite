package controller.friends;

import com.google.gson.Gson;
import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(value = "/getReceivedFriendRequests", name = "getReceivedFriendRequests")
public class GetFriendRequests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = ((User) req.getSession().getAttribute("user")).getId();
        ArrayList<User> requests = DAOFactory.getInstance().makeUserDAO().getFriendsRequests(id);
        Gson gson = new Gson();
        String json = gson.toJson(requests);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);
    }
}
