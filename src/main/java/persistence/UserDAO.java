package persistence;

import model.Game;
import model.User;
import org.apache.commons.io.IOUtils;
import org.postgresql.util.PGbytea;
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

    public ArrayList<User> getFriends(User user) {
        ArrayList<User> friends = new ArrayList<User>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT u.* FROM public.user as u, friends as uf WHERE uf.id_user1 = ? and u.id = uf.id_user2";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isClosed())
                return null;
            while (resultSet.next()) {
                friends.add(createSimpleUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public byte[] getProfilePicture(User u) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT profile_image FROM public.user where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, u.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getBytes("profileimage");
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
        return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"),
                EncryptDecryptAES128.getInstance().decrypt(resultSet.getString("password")), resultSet.getString("description"), resultSet.getBytes("profile_image"));
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

    public User getUserByEmail(String email) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.user where email=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return createSimpleUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUser(String email, String username, String password, String description) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.user(id, email, username, password, description) values(default,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, EncryptDecryptAES128.getInstance().encrypt(password));
            statement.setString(4, description);
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

    public void changeUserDetails(User u) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET username = ?, email = ?, description = ?, password = ?, profile_image = ? WHERE id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, u.getUsername());
            statement.setString(2, u.getEmail());
            statement.setString(3, u.getDescription());
            statement.setString(4, EncryptDecryptAES128.getInstance().encrypt(u.getPassword()));
            statement.setBytes(5, u.getImage());
            statement.setInt(6, u.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserFriend(int from, int to) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO friends(id, id_user1, id_user2, date) values(default, ?, ?, default)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(from));
            statement.setString(2, Integer.toString(to));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeProfileImageUser(int idUser, InputStream fileContent) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET profile_image = ? WHERE id = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setBytes(1, IOUtils.toByteArray(fileContent));
            statement.setString(2, Integer.toString(idUser));
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
