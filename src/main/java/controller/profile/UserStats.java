package controller.profile;

import com.google.gson.Gson;
import model.Game;
import model.User;
import persistence.DAOFactory;
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
        User u = (User)req.getSession().getAttribute("user");
        int id = u.getId();
        float totalHours = 0f;
        TreeMap<String, Integer> hoursPlayedYear = DAOFactory.getInstance().makeHoursPlayedDAO().getHoursPlayedFromIdUser(id);
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
        ArrayList<Game> userLibrary = DAOFactory.getInstance().makeUserDAO().getGames(u);
        ArrayList<TreeMap<Integer, Integer>> arrayListHashMaps = new ArrayList<>();
        for (Game g : userLibrary){
            arrayListHashMaps.add(DAOFactory.getInstance().makeHoursPlayedDAO().getHoursPlayedYEARGAMEbyIdUser(u.getId(),g.getId()));
        }
        ArrayList<Set<Integer>> hashMapHoursKeys = new ArrayList<>();
        for (TreeMap<Integer, Integer> map : arrayListHashMaps){
            hashMapHoursKeys.add(map.keySet());
        }
        ArrayList<Collection<Integer>> hashMapHoursValues = new ArrayList<>();
        for (TreeMap<Integer, Integer> map : arrayListHashMaps){
            hashMapHoursValues.add(map.values());
        }
        req.setAttribute("totalGameHoursPlayed", DAOFactory.getInstance().makeHoursPlayedDAO().getTotalHoursPlayedByUser(id));
        req.setAttribute("arrayHashMapHoursKeys",hashMapHoursKeys);
        req.setAttribute("arrayHashMapHoursValues", hashMapHoursValues);
        req.setAttribute("hoursPlayedKeys", hoursPlayedYear.keySet());
        req.setAttribute("hoursPlayedValues", hoursPlayedYear.values());
        req.setAttribute("totalHoursPlayed", totalHours);
        req.setAttribute("bestScoreName", bestScore.getSecond());
        req.setAttribute("bestScoreValue", bestScore.getFirst());
        RequestDispatcher rd = req.getRequestDispatcher("userStats.jsp");
        rd.include(req,resp);
    }
}
