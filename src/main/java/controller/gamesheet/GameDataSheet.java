package controller.gamesheet;

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

@WebServlet(value="/GameDataSheet", name = "gameDataSheet")
public class GameDataSheet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gameId = Integer.parseInt(req.getParameter("gameId"));
        Game game = DAOFactory.getInstance().makeGameDAO().getGameById(gameId);
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("gameId", gameId);
        req.setAttribute("gamePrice", game.getPrice());
        if(user != null) {
            boolean canBuy = DAOFactory.getInstance().makeGameDAO().isGamePurchased(gameId, user.getId());
            req.setAttribute("canBuy", canBuy);
        }
        RequestDispatcher rd = req.getRequestDispatcher("gameDataSheet.jsp");
        rd.forward(req, resp);
    }

}
