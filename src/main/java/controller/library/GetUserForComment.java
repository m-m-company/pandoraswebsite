package controller.library;

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
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value = "/getUserForComment", name = "getUserForComment")
public class GetUserForComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(401);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = null;
        user = DAOFactory.getInstance().makeUserDAO().getUserById(id);
        String actualUser = "false";
        if(user.getId() == ((User) req.getSession().getAttribute("user")).getId()){
            actualUser = "true";
        }
        ArrayList<String> data = new ArrayList<>();
        data.add(String.valueOf(user.getId()));
        data.add(user.getUsername());
        data.add(actualUser);
        if(user.isGoogleUser()){
            data.add(DAOFactory.getInstance().makeUserDAO().getGoogleUrlImage(user.getId()));
        }
        else{
            data.add(String.valueOf(user.getImage()));
        }
        Gson gson = new Gson();
        String response = gson.toJson(data);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(response);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);
    }

}
