package controller.authentication;

import persistence.DAOFactory;
import utility.GoogleToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/googleLogin", name = "googleLogin")
public class GoogleLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if(GoogleToken.getInstance().verifyToken(token)){
            String id = GoogleToken.getInstance().getId();
            String url = GoogleToken.getInstance().getUrlImage();
            if(!(DAOFactory.getInstance().makeUserDAO().googleIdAlreadyExists(id))){
                String email = GoogleToken.getInstance().getEmail();
                if(DAOFactory.getInstance().makeUserDAO().getUserByEmail(email) == null){
                    String username = GoogleToken.getInstance().getUsername();
                    String description = "Ciao, sono " + username;
                    DAOFactory.getInstance().makeUserDAO().insertUser(email, username, "", description, true);
                    DAOFactory.getInstance().makeUserDAO().insertGoogleUser(id, email, url);
                }
            }
            else{
                DAOFactory.getInstance().makeUserDAO().updateGoogleUrl(url, id);
            }
            resp.setStatus(201);
        }
        else{
            resp.setStatus(403);
        }
    }

}
