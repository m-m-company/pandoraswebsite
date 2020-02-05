package controller.profile;

import model.Game;
import model.User;
import persistence.DAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

@WebServlet(value = "/devStats")
public class DevStats extends HttpServlet
{
    private ArrayList<Integer> createAverageStarring(User loggedUser){
        ArrayList<Integer> averages = new ArrayList<>();
        averages.add(null);
        averages.add(null);
        averages.addAll(DAOFactory.getInstance().makeReviewDAO().getAvgReviewsByIdUser(loggedUser.getId()));
        return averages;
    }

    private ArrayList<TreeMap<String, Integer>> createStarring(ArrayList<Game> uploadedGames){
        ArrayList<TreeMap<String, Integer>> starring = new ArrayList<>();
        starring.add(null);
        starring.add(null);
        for (Game game : uploadedGames)
            starring.add(DAOFactory.getInstance().makeReviewDAO().getReviewsTreeMapByIdGame(game.getId()));
        this.log(String.valueOf(starring));
        return starring;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        RequestDispatcher rd = null;
        User loggedUser = (User) req.getSession().getAttribute("user");
        rd = req.getRequestDispatcher("header.jsp");
        rd.include(req, resp);
        ArrayList<Game> uploadedGames = new ArrayList<>(DAOFactory.getInstance().makeGameDAO().getUploadedGamesById(loggedUser.getId()));
        req.setAttribute("uploadedGames", uploadedGames);
        req.setAttribute("averageStarring",createAverageStarring(loggedUser));
        ArrayList<TreeMap<String, Integer>> starring = createStarring(uploadedGames);
        ArrayList<Set<String>> starringKeys = new ArrayList<>();
        ArrayList<Collection<Integer>> starringValue = new ArrayList<>();
        for (TreeMap<String, Integer> s : starring) {
            if (s != null) {
                starringKeys.add(s.keySet());
                starringValue.add(s.values());
            }
        }
        req.setAttribute("starringKeys",starringKeys);
        req.setAttribute("starringValues",starringValue);

        rd = req.getRequestDispatcher("devStats.jsp");
        rd.include(req, resp);
    }
}
