package controller;

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
        setCategory(req);
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }

    private void setBestsellers(HttpServletRequest req){
        ArrayList<Pair<Integer,String>> bestSellers = DAOFactory.getInstance().makePurchaseDAO().getBestSellers();
        req.setAttribute("firstGameBestSellers", bestSellers.get(0));
        req.setAttribute("secondGameBestSellers", bestSellers.get(1));
        //req.setAttribute("thirdGameBestSellers", bestSellers.get(2));
    }

    private void setCategory(HttpServletRequest req){
        ArrayList<String> categories = DAOFactory.getInstance().makeTagDao().getTagsList();
        req.setAttribute("categories", categories);
        for(int id = 1; id <= categories.size(); ++id){
            setGamesCategory(id, categories.get(id-1), req);
        }
    }

    private void setGamesCategory(int tag, String tagName, HttpServletRequest req)
    {
        ArrayList<Pair<Integer,String>> games = DAOFactory.getInstance().makeGameDAO().getAllGamesFromCategory(tag);
        String category = "category-"+tagName;
        req.setAttribute(category, games);
        ArrayList<Integer> length = new ArrayList<>();
        for(int i = 0; i < games.size() / 6; ++i){
            length.add(i);
        }
        String lengthAttribute = "lengthGamesDiv6" + tagName;
        req.setAttribute(lengthAttribute, length);
    }
}