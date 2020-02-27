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

@WebServlet(value = "/devStats", name = "devStats")
public class DevStats extends HttpServlet {

    private ArrayList<Integer> createAverageStarring(User loggedUser) {
        ArrayList<Integer> averages = new ArrayList<>();
        averages.add(null);
        averages.add(null);
        averages.addAll(DAOFactory.getInstance().makeReviewDAO().getAvgReviewsByIdUser(loggedUser.getId()));
        this.log("AVERAGES STARRING" + averages);
        return averages;
    }

    private ArrayList<Integer> createSellsPerGame(User loggedUser) {
        ArrayList<Integer> sells = new ArrayList<>();
        sells.add(null);
        sells.add(null);
        sells.addAll(DAOFactory.getInstance().makePurchaseDAO().getSellsByIdUser(loggedUser.getId()));
        this.log("SELLS" + sells);
        return sells;
    }

    private ArrayList<TreeMap<String, Integer>> createStarring(ArrayList<Game> uploadedGames) {
        ArrayList<TreeMap<String, Integer>> starring = new ArrayList<>();
        starring.add(null);
        starring.add(null);
        for (Game game : uploadedGames)
            starring.add(DAOFactory.getInstance().makeReviewDAO().getReviewsTreeMapByIdGame(game.getId()));
        this.log(String.valueOf(starring));
        return starring;
    }

    private ArrayList<TreeMap<String, Integer>> createSells(ArrayList<Game> uploadedGames) {
        ArrayList<TreeMap<String, Integer>> sells = new ArrayList<>();
        sells.add(null);
        sells.add(null);
        for (Game game : uploadedGames)
            sells.add(DAOFactory.getInstance().makePurchaseDAO().getSellsByIdGame(game.getId()));
        return sells;
    }


    private ArrayList<TreeMap<String, Double>> createPrices(ArrayList<Game> uploadedGames) {
        ArrayList<TreeMap<String, Double>> prices = new ArrayList<>();
        prices.add(null);
        prices.add(null);
        for (Game game : uploadedGames)
            prices.add(DAOFactory.getInstance().makePurchaseDAO().getPricesByIdGame(game.getId()));
        this.log(String.valueOf(prices));
        return prices;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = null;
        User loggedUser = (User) req.getSession().getAttribute("user");
        rd = req.getRequestDispatcher("header.jsp");
        rd.include(req, resp);
        if ((Boolean)req.getSession().getAttribute("logged") == null || !(Boolean)req.getSession().getAttribute("logged")) {
            rd = req.getRequestDispatcher("errorNotLogged.html");
            rd.include(req,resp);
            rd = req.getRequestDispatcher("footer.html");
            rd.include(req, resp);
            return;
        }
        ArrayList<Game> uploadedGames = new ArrayList<>(DAOFactory.getInstance().makeGameDAO().getUploadedGamesById(loggedUser.getId()));
        req.setAttribute("uploadedGames", uploadedGames);

        //Starrings
        ArrayList<TreeMap<String, Integer>> starring = createStarring(uploadedGames);
        ArrayList<Set<String>> starringKeys = new ArrayList<>();
        ArrayList<Collection<Integer>> starringValue = new ArrayList<>();
        for (TreeMap<String, Integer> s : starring) {
            if (s != null) {
                starringKeys.add(s.keySet());
                starringValue.add(s.values());
            }else {
                starringKeys.add(null);
                starringValue.add(null);
            }
        }
        req.setAttribute("averageStarring", createAverageStarring(loggedUser));
        req.setAttribute("starringKeys", starringKeys);
        req.setAttribute("starringValues", starringValue);
        //

        //Sells
        int totalSells = DAOFactory.getInstance().makePurchaseDAO().getTotalSellsByIdUser(loggedUser.getId());
        ArrayList<Integer> sellsPerGame = createSellsPerGame(loggedUser);
        ArrayList<TreeMap<String, Integer>> sellsArray = createSells(uploadedGames);
        ArrayList<Set<String>> sellsKeys = new ArrayList<>();
        ArrayList<Collection<Integer>> sellsValues = new ArrayList<>();
        for (TreeMap<String, Integer> sells : sellsArray) {
            if (sells != null) {
                sellsKeys.add(sells.keySet());
                sellsValues.add(sells.values());
            }else {
                sellsKeys.add(null);
                sellsValues.add(null);
            }
        }
        req.setAttribute("totalSells", totalSells);
        req.setAttribute("sellsPerGame", sellsPerGame);
        req.setAttribute("sellsKeys", sellsKeys);
        req.setAttribute("sellsValues", sellsValues);
        //

        //Earnings
        int totalEarnings = DAOFactory.getInstance().makePurchaseDAO().getEarningsByIdUser(loggedUser.getId());
        req.setAttribute("totalEarnings", totalEarnings);
        //

        //Price
        ArrayList<TreeMap<String, Double>> pricesArray = createPrices(uploadedGames);
        ArrayList<Set<String>> pricesKeys = new ArrayList<>();
        ArrayList<Collection<Double>> pricesValues = new ArrayList<>();
        for (TreeMap<String, Double> prices : pricesArray) {
            if (prices != null) {
                pricesKeys.add(prices.keySet());
                pricesValues.add(prices.values());
            }else {
                pricesKeys.add(null);
                pricesValues.add(null);
            }
        }
        req.setAttribute("pricesKeys", pricesKeys);
        req.setAttribute("pricesValues", pricesValues);
        rd = req.getRequestDispatcher("devStats.jsp");
        rd.include(req, resp);
    }

}
