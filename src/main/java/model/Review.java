package model;

import java.sql.Date;

public class Review {

    private int id;
    private String username;
    private int author;
    private int idGame;
    private int stars;
    private String comment;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;

    public Review(int id, int author, int idGame, int stars, Date date, String comment) {
        this.id = id;
        this.author = author;
        this.idGame = idGame;
        this.stars = stars;
        this.date = date;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Review() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int numStars) { this.stars = numStars;}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
