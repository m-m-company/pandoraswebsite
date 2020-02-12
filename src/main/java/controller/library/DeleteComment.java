package controller.library;

import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteComment", name = "deleteComment")
public class DeleteComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if(DAOFactory.getInstance().makeReviewDAO().deleteComment(id)){
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
