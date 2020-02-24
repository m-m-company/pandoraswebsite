package utility;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleToken {

    private static GoogleToken instance = null;
    private GoogleIdTokenVerifier verifier = null;
    private GoogleIdToken.Payload payload = null;

    private GoogleToken() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance()).
                setAudience(Collections.singletonList("254902414036-0872j3k7esabpdm90bjauogg7qq3eebq.apps.googleusercontent.com")).
                build();
    }

    public static GoogleToken getInstance() {
        if (instance == null){
            instance = new GoogleToken();
        }
        return instance;
    }

    public boolean verifyToken(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null){
                payload = idToken.getPayload();
                return true;
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getId(){
        return payload.getSubject();
    }

    public String getEmail() {
        return payload.getEmail();
    }

    public String getUsername() {
        return (String) payload.get("name");
    }

    public String getUrlImage() {
        return (String) payload.get("picture");
    }

}
