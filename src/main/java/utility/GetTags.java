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
import java.util.ArrayList;

@WebServlet(value = "/getTags", name = "getTags")
public class GetTags extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> tags = DAOFactory.getInstance().makeTagDao().getTagsList();
        Gson gson = new Gson();
        String json = gson.toJson(tags);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);
    }
}
