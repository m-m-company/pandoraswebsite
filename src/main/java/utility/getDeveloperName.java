package utility;

import com.google.gson.Gson;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/getDeveloperName", name = "getDeveloperName")
public class getDeveloperName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = DAOFactory.getInstance().makeUserDAO().getUserById(Integer.parseInt(req.getParameter("idDeveloper"))).getUsername();
        Gson gson = new Gson();
        String json = gson.toJson(name);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);

    }
}
