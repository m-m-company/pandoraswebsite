package persistence;

import model.Game;
import model.User;
import org.apache.commons.io.IOUtils;
import utility.EncryptDecryptAES128;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    private PreparedStatement statement;

    public ArrayList<User> getFriends(int id) {
        ArrayList<User> friends = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT u.* FROM public.user as u, friends as f WHERE f.id_user1 = ? and u.id = f.id_user2";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                friends.add(createSimpleFriend(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    private User createSimpleFriend(ResultSet resultSet) throws SQLException {
        User friend = new User();
        friend.setId(resultSet.getInt("id"));
        friend.setUsername(resultSet.getString("username"));
        friend.setDescription(resultSet.getString("description"));
        friend.setGoogleUser(resultSet.getBoolean("google_user"));
        friend.setEmail(resultSet.getString("email"));
        friend.setImage(resultSet.getBytes("profile_image") != null);
        return friend;
    }

    public byte[] getProfilePicture(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT profile_image FROM public.user where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBytes("profile_image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getGames(User u) {
        Connection connection = DbAccess.getConnection();
        ArrayList<Game> library = new ArrayList<>();
        String query = "SELECT game.* FROM library, game where id_user= ? AND id_game = game.id";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, u.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                library.add(DAOFactory.getInstance().makeGameDAO().createSimpleGame(resultSet));
            }
            return library;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User createSimpleUser(ResultSet resultSet) throws SQLException {
        if (resultSet.getInt("id") == 0)
            return null;
        boolean image = false;
        if (resultSet.getBytes("profile_image") != null) {
            image = true;
        }
        return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"),
                EncryptDecryptAES128.getInstance().decrypt(resultSet.getString("password")), resultSet.getString("description"), image, resultSet.getBoolean("google_user"));
    }

    public User getUserById(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.user where id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createSimpleUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.user where email=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createSimpleUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUser(String email, String username, String password, String description, boolean googleUser) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.user(id, email, username, password, description, google_user) values(default,?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, EncryptDecryptAES128.getInstance().encrypt(password));
            statement.setString(4, description);
            statement.setBoolean(5, googleUser);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertGoogleUser(String id, String email, String url) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO id_google(email, url_image, id) values(?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, url);
            statement.setString(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(User user, String newPassword) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET password=? WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, EncryptDecryptAES128.getInstance().encrypt(newPassword));
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserDetails(User u, byte[] image) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET username = ?, email = ?, description = ?, password = ?, profile_image = ? WHERE id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, u.getUsername());
            statement.setString(2, u.getEmail());
            statement.setString(3, u.getDescription());
            statement.setString(4, EncryptDecryptAES128.getInstance().encrypt(u.getPassword()));
            statement.setBytes(5, image);
            statement.setInt(6, u.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean googleIdAlreadyExists(String id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM id_google WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateGoogleUrl(String url, String id) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE id_google SET url_image=? WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, url);
            statement.setString(2, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGoogleUrlImage(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT g.url_image FROM public.user as u, id_google as g WHERE u.id=? AND u.email=g.email";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getString("url_image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteFriend(int idActual, int idFriend) {
        Connection connection = DbAccess.getConnection();
        String query = "DELETE FROM friends WHERE id_user1=? AND id_user2=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, idActual);
            statement.setInt(2, idFriend);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<User> getUsersByUsername(String username, int id) {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.user WHERE username ILIKE ? AND id<>?";
        username = "%" + username + "%";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                users.add(createSimpleFriend(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean sendFriendRequest(int userID, int friendID) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO friend_request(id, \"from\", \"to\", accepted) VALUES (default,?,?,default)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, friendID);
            if(statement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFriendRequest(int userID, int friendID) {
        Connection connection = DbAccess.getConnection();
        String query = "DELETE FROM friend_request WHERE (\"from\"=? AND \"to\"=?) OR (\"from\"=? AND \"to\"=?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, friendID);
            statement.setInt(3, friendID);
            statement.setInt(4, userID);

            if(statement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<User> getFriendsRequests(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT public.user.* FROM public.user, friend_request WHERE public.user.id = friend_request.from AND friend_request.to = ?";
        ArrayList<User> requests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                requests.add(createSimpleFriend(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean acceptFriendRequest(int from, int to) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE friend_request SET accepted=true WHERE \"from\"=? AND \"to\"=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, from);
            statement.setInt(2, to);
            if(statement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<User> getSentFriendRequests(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT public.user.* FROM public.user, friend_request WHERE public.user.id = friend_request.to AND friend_request.from = ?";
        ArrayList<User> requests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                requests.add(createSimpleFriend(resultSet));
            }
            return requests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}