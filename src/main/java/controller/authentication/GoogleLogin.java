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
        if(!(DAOFactory.getInstance().makeUserDAO().googleIdAlreadyExists(token))){
            if(!GoogleToken.getInstance().verifyToken(token)){
                //TODO: da gestire
                this.log("verifica del token fallita");
            }
            String email = GoogleToken.getInstance().getEmail();
            String username = GoogleToken.getInstance().getUsername();
            String description = "Ciao, sono " + username;
            if(DAOFactory.getInstance().makeUserDAO().getUserByEmail(email) == null){
                DAOFactory.getInstance().makeUserDAO().insertUser(email, username, "", description, true);
                DAOFactory.getInstance().makeUserDAO().insertGoogleUser(token, email);
            }
            else{
                //TODO: da gestire
                this.log("email google non trovata nel database");
            }
        }
        resp.setStatus(201);
    }

}
