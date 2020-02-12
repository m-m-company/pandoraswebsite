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
            String token = DAOFactory.getInstance().makeUserDAO().getGoogleToken(u.getEmail());
            if(!(GoogleToken.getInstance().verifyToken(token))){
                //TODO: da verificare
                this.log("token non valido per stampare l'immagine");
            }
        }
        else {
            u = (User) req.getSession().getAttribute("user");
        }
        String url = GoogleToken.getInstance().getUrlImage();
        resp.setContentType("text/html");
        String c = req.getParameter("class");
        String width = req.getParameter("width");
        String height = req.getParameter("height");
        resp.getWriter().write("<img class=\""+c+"\" src=\""+url+"\" width=\""+width+"\" height=\""+height+"\">");
    }

}
