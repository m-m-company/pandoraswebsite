package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private int id;
    private String name;
    private String frontImage;
    private String paymentEmail;
    private String supportEmail;
    private double price;
    private double sale;
    private Date date;
    private int idDeveloper;
    private String description;
    private String specifics;

    public Game() {
    }

    public Game(int id, String name, String frontImage, String paymentEmail, String supportEmail, double price, double sale, Date date, int idDeveloper, String description, String specifics) {
        this.id = id;
        this.name = name;
        this.frontImage = frontImage;
        this.paymentEmail = paymentEmail;
        this.supportEmail = supportEmail;
        this.price = price;
        this.sale = sale;
        this.date = date;
        this.idDeveloper = idDeveloper;
        this.description = description;
        this.specifics = specifics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getPaymentEmail() {
        return paymentEmail;
    }

    public void setPaymentEmail(String paymentEmail) {
        this.paymentEmail = paymentEmail;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

}
