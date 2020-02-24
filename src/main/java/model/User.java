package model;

import persistence.DAOFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class User {

	private int id;
	private String username;
	private String email;
	private String password;
	private String description;
	private boolean image;
	private boolean googleUser;


	public User() {}

	public User(int id, String username, String email, String password, String description, boolean image, boolean googleUser) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.description = description;
		this.email = email;
		this.image = image;
		this.googleUser = googleUser;
	}

	public ArrayList<Game> getLibrary() {
		return DAOFactory.getInstance().makeUserDAO().getGames(this);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getImage() {
		return image;
	}
	public void setImage(boolean image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isGoogleUser() {
		return googleUser;
	}

	public void setGoogleUser(boolean googleUser) {
		this.googleUser = googleUser;
	}

}
