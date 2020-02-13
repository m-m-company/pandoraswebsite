package utility;

import model.User;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/printGoogleImage", name = "printGoogleImage")
public class PrintGoogleImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url;
        int id;
        if(req.getParameter("id") != null){
            id = Integer.parseInt(req.getParameter("id"));
        }
        else {
            id = ((User) req.getSession().getAttribute("user")).getId();
        }
        url = DAOFactory.getInstance().makeUserDAO().getGoogleUrlImage(id);
        resp.setContentType("text/html");
        String c = req.getParameter("class");
        String width = req.getParameter("width");
        String height = req.getParameter("height");
        resp.getWriter().write("<img class=\""+c+"\" src=\""+url+"\" width=\""+width+"\" height=\""+height+"\">");
    }

}
