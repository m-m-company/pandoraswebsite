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
        ArrayList<Game> userLibrary = DAOFactory.getInstance().makeUserDAO().getGames(u);
        ArrayList<TreeMap<String, Integer>> scoresArray = new ArrayList<>();
        ArrayList<Set<String>> scoresKeys = new ArrayList<>();
        ArrayList<Collection<Integer>> scoresValues = new ArrayList<>();
        ArrayList<TreeMap<Integer, Integer>> arrayListHashMaps = new ArrayList<>();
        ArrayList<Set<Integer>> hashMapHoursKeys = new ArrayList<>();
        ArrayList<Collection<Integer>> hashMapHoursValues = new ArrayList<>();
        float totalHours = 0f;
        TreeMap<String, Integer> hoursPlayedYear = DAOFactory.getInstance().makeHoursPlayedDAO().getHoursPlayedFromIdUser(u.getId());
        for(String game: hoursPlayedYear.keySet())
        {
            totalHours += hoursPlayedYear.get(game);
        }
        userLibrary.forEach(game ->{
            scoresArray.add(DAOFactory.getInstance().makeScoreDAO().getScoresByIdUserForGameId(u.getId(), game.getId()));
            arrayListHashMaps.add(DAOFactory.getInstance().makeHoursPlayedDAO().getHoursPlayedYEARGAMEbyIdUser(u.getId(),game.getId()));
        });
        scoresArray.forEach(stringIntegerTreeMap -> {
            scoresKeys.add(stringIntegerTreeMap.keySet());
            scoresValues.add(stringIntegerTreeMap.values());
        });
        arrayListHashMaps.forEach(integerIntegerTreeMap -> {
            hashMapHoursKeys.add(integerIntegerTreeMap.keySet());
            hashMapHoursValues.add(integerIntegerTreeMap.values());
        });
        req.setAttribute("totalGameHoursPlayed", DAOFactory.getInstance().makeHoursPlayedDAO().getTotalHoursPlayedByUser(u.getId()));
        req.setAttribute("arrayHashMapHoursKeys",hashMapHoursKeys);
        req.setAttribute("arrayHashMapHoursValues", hashMapHoursValues);
        req.setAttribute("hoursPlayedKeys", hoursPlayedYear.keySet());
        req.setAttribute("hoursPlayedValues", hoursPlayedYear.values());
        req.setAttribute("totalHoursPlayed", totalHours);
        req.setAttribute("scoresKeys", scoresKeys);
        req.setAttribute("scoresValues", scoresValues);
        RequestDispatcher rd = req.getRequestDispatcher("userStats.jsp");
        rd.include(req,resp);
    }
}
