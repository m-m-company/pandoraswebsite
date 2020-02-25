package controller;

import model.Game;
import persistence.DAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value="/searchGames", name = "searchGames")
public class SearchGame extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gameName = req.getParameter("gameName");
        String category = req.getParameter("category");
        String price = req.getParameter("price");
        String rating = req.getParameter("rating");
        ArrayList<Game> games = DAOFactory.getInstance().makeGameDAO().getFilterGames(gameName, category, price, rating);
        this.log(games.toString());
        req.setAttribute("games", games);
        req.getRequestDispatcher("searchGame.jsp").forward(req, resp);
    }

}
