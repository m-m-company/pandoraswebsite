package controller.library;

import com.google.gson.Gson;
import model.Game;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

@WebServlet(value = "/getPreviewGame", name = "getPreviewGame")
public class GetPreviewGame extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> files= new ArrayList<>();
        Game g = null;
        if (req.getParameter("idGame") != null){
            g = DAOFactory.getInstance().makeGameDAO().getGameById(Integer.parseInt(req.getParameter("idGame")));
        }else {
            String game = req.getParameter("name");
            g = DAOFactory.getInstance().makeGameDAO().getGameByName(game);
        }
        Gson gson = new Gson();
        String json = gson.toJson(g.getPreviews(this.getServletContext()));
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
    }
}
