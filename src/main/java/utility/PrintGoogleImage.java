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
        User u;
        if(req.getParameter("id") != null){
            int userId = Integer.parseInt(req.getParameter("id"));
            u = DAOFactory.getInstance().makeUserDAO().getUserById(userId);
        }
        else {
            u = (User) req.getSession().getAttribute("user");
        }
        String token = DAOFactory.getInstance().makeUserDAO().getGoogleToken(u.getEmail());
        if(!GoogleToken.getInstance().verifyToken(token)){
            //TODO: da gestire
            this.log("token non valido per stampare l'immagine");
        }
        String url = GoogleToken.getInstance().getUrlImage();
        resp.setContentType("text/html");
        resp.getWriter().write("<img class=\"dropdown-image\" src=\""+url+"\" width=\"50\" height=\"50\">");
    }

}
