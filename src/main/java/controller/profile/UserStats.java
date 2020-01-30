package controller.profile;

import model.Game;
import model.User;
import persistence.DAOFactory;
import persistence.UserDAO;
import utility.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/UserStats")
public class UserStats extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = ((User)req.getSession().getAttribute("user")).getId();
        float totalHours = 0f;
        HashMap<String, Integer> hoursPlayedYear = DAOFactory.getInstance().makeHoursPlayedDAO().getHoursPlayedFromIdUser(id);
        for(String game: hoursPlayedYear.keySet())
        {
            totalHours += hoursPlayedYear.get(game);
        }
        ArrayList<Pair<Integer, String>> gameScore = DAOFactory.getInstance().makeScoreDAO().getScoresFromIdUser(id);
        Pair<Integer, String> bestScore = new Pair<Integer, String>(0,"");
        //bestScore.setFirst(gameScore.get(0).getFirst());
        //bestScore.setSecond(gameScore.get(0).getSecond());
        for(Pair<Integer,String> p: gameScore)
        {
            if(p.getFirst() > bestScore.getFirst())
            {
                bestScore.setSecond(p.getSecond());
                bestScore.setFirst(p.getFirst());
            }
        }
        req.setAttribute("hoursPlayedKeys", hoursPlayedYear.keySet());
        req.setAttribute("hoursPlayedValues", hoursPlayedYear.values());
        req.setAttribute("totalHoursPlayed", totalHours);
        req.setAttribute("bestScoreName", bestScore.getSecond());
        req.setAttribute("bestScoreValue", bestScore.getFirst());
        RequestDispatcher rd = req.getRequestDispatcher("userStats.jsp");
        rd.include(req,resp);
    }
}
