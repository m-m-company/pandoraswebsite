package controller.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import persistence.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@WebServlet(value = "/googleLogin", name = "googleLogin")
public class GoogleLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance()).
                setAudience(Collections.singletonList("254902414036-0872j3k7esabpdm90bjauogg7qq3eebq.apps.googleusercontent.com")).
                build();
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if(idToken != null){
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();
                String username = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                if(DAOFactory.getInstance().makeUserDAO().googleIdAlreadyExists(Integer.parseInt(userId))){
                    //TODO: login
                }
                else{
                    //TODO: register
                }
            } else {
                //TODO: da gestire
                this.log("non valido");
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

}
