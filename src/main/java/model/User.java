package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private String description;
	private ArrayList<User> friends;
	private ArrayList<Game> library;
	private byte[] image;


	public User() {}

	public User(int id, String username, String email, String password, String description, byte[] image) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.description = description;
		this.email = email;
		this.image = image;
	}

	public ArrayList<Game> getLibrary() {
		return library;
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
	public ArrayList<User> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLibrary(ArrayList<Game> library) {
		this.library = library;
	}

	public boolean addFriend(User u)
	{
		for(User user: friends)
		{
			if(user.getId() == u.getId())
				return false;
		}
		this.friends.add(u);
		return true;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", description='" + description + '\'' +
				", friends=" + friends +
				", library=" + library +
				", image=" + Arrays.toString(image) +
				", email='" + email + '\'' +
				'}';
	}
}
