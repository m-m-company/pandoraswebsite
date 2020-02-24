package controller.gamesheet;

import model.Game;
import model.Review;
import model.Score;
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
import java.util.Collections;

@WebServlet(value="/GameDataSheet", name = "gameDataSheet")
public class GameDataSheet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gameId = Integer.parseInt(req.getParameter("gameId"));
        Game game = DAOFactory.getInstance().makeGameDAO().getGameById(gameId);
        String usernameDeveloper = (DAOFactory.getInstance().makeUserDAO().getUserById(game.getIdDeveloper())).getUsername();
        ArrayList<Review> reviews = DAOFactory.getInstance().makeReviewDAO().getReviewsByIdGame(gameId);
        ArrayList<Score> scores = DAOFactory.getInstance().makeScoreDAO().getScoresFromIdGame(gameId);
        sortScores(scores);
        if(scores.size() >= 10)
            scores = (ArrayList<Score>) scores.subList(0,9);
        User user = (User) req.getSession().getAttribute("user");
        if(user != null) {
            boolean canBuy = DAOFactory.getInstance().makeGameDAO().isGamePurchased(gameId, user.getId());
            req.setAttribute("canBuy", canBuy);
        }

        ArrayList<Integer> totalSize = new ArrayList<Integer>();
        ArrayList<String> previews = game.getPreviews(this.getServletContext());
        ArrayList<String> externalLinks = DAOFactory.getInstance().makeGameDAO().getExternalLinks(gameId);
        for(int i = 0; i < previews.size()+externalLinks.size();i++)
            totalSize.add(i);
        req.setAttribute("previews", previews);
        req.setAttribute("externalLinks", externalLinks);
        ArrayList<String> tags = DAOFactory.getInstance().makeTagDao().getTagsForGame(game.getId());
        req.setAttribute("tags", tags);
        req.setAttribute("game", game);
        req.setAttribute("totalSize", totalSize);
        req.setAttribute("developer", usernameDeveloper);
        req.setAttribute("reviews", reviews);
        req.setAttribute("ranking", scores);
        RequestDispatcher rd = req.getRequestDispatcher("gameDataSheet.jsp");
        rd.forward(req, resp);
    }

    private void sortScores(ArrayList<Score> scores) {
        Collections.sort(scores, (a, b) -> a.getValue() > b.getValue() ? -1 : a.getUsername().compareTo(b.getUsername()));
    }

}
