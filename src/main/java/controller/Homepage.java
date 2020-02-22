package controller;

import model.Game;
import persistence.DAOFactory;
import utility.Pair;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("")
public class Homepage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setBestsellers(req);
        req.setAttribute("hashMapTagsAndGames", DAOFactory.getInstance().makeGameDAO().getTagsAndGames());
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }

    private void setBestsellers(HttpServletRequest req){
        ArrayList<Game> bestSellers = DAOFactory.getInstance().makePurchaseDAO().getBestSellers();
        req.setAttribute("bestSellers", bestSellers);
    }
}