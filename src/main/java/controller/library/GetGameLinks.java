package controller.library;

import com.google.gson.Gson;
import model.Game;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/getGameLinks")
public class GetGameLinks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        ArrayList<String> links = DAOFactory.getInstance().makeGameDAO().getExternalLinks(Integer.parseInt(req.getParameter("gameID")));
        ArrayList<String> videoID = new ArrayList<>();
        ArrayList<String> others = new ArrayList<>();
        for (String link: links)
        {
            if (link.contains("youtube")) {
                String[] id = link.split("[a-z]=");
                if (id[1].charAt(id[1].length() - 1) == '&') {
                    id[1] = id[1].substring(0, id[1].length()-1);
                }
                videoID.add(id[1]);
            }else{
                others.add(link);
            }
        }
        ArrayList<ArrayList<String>> merge = new ArrayList<>();
        merge.add(videoID);
        merge.add(others);
        String json = gson.toJson(merge);
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
        resp.setStatus(201);
    }
}
