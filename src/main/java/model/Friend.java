package model;

public class Friend {

    private int id;
    private String username;
    private String email;
    private String description;
    private boolean googleUser;

    public Friend() {
    }

    public Friend(int id, String username, String email, String description, boolean googleUser) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.googleUser = googleUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(boolean googleUser) {
        this.googleUser = googleUser;
    }
}
