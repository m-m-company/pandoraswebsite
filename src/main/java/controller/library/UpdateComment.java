package controller.library;

import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/updateComment", name = "updateComment")
public class UpdateComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idReview = Integer.parseInt(req.getParameter("idReview"));
        String content = req.getParameter("content");
        if(DAOFactory.getInstance().makeReviewDAO().updateComment(idReview, content)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
