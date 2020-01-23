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
    public ArrayList<User> getFriends(User user) throws SQLException {
        ArrayList<User> friends = new ArrayList<User>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT u.* FROM public.user as u, public.friends as uf WHERE uf.id_user1 = ?::integer and u.id = uf.id_user2";
        statement = connection.prepareStatement(query);
        statement.setString(1, Integer.toString(user.getId()));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.isClosed())
            return null;
        while (resultSet.next()) {
            friends.add(createSimpleUser(resultSet));
        }
        return friends;
    }

    public byte[] getProfilePicture(User u) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT profileimage FROM public.user where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, u.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return resultSet.getBytes("profileimage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return null;
    }

    public ArrayList<Game> getGames(User u) throws SQLException {
        Connection connection = DbAccess.getConnection();
        ArrayList<Game> library = new ArrayList<>();
        String query = "SELECT * FROM public.library where id_user= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,u.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                library.add(DAOFactory.getInstance().makeGameDAO().createSimpleGame(resultSet));
            }
            return library;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return null;
    }

    private User createSimpleUser(ResultSet resultSet) throws SQLException {
        if (resultSet.getInt("id") == 0)
            return null;
        return new User(resultSet.getInt("id"), resultSet.getString("username"),
                EncryptDecryptAES128.getInstance().decrypt(resultSet.getString("password")), resultSet.getString("description"),
                resultSet.getString("email"));
    }

    public User getUserById(int id) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.user where id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return createSimpleUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.close();
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
        }finally {
            connection.close();
        }
        return null;
    }

    public User checkLogin(String email, String password){
        try {
            User u = this.getUserByEmail(email);
            if (u.getPassword().equals(password)){
                return u;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUser(User user) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.user(id, email, username, password, description) values(default,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, EncryptDecryptAES128.getInstance().encrypt(user.getPassword()));
            statement.setString(4, user.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void changePassword(User user, String newPassword) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET password=? WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, EncryptDecryptAES128.getInstance().encrypt(newPassword));
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void changeUserDetails(User u) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE public.user SET username = ?, email = ?, description = ?, password = ?  WHERE id = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, u.getUsername());
            statement.setString(2, u.getEmail());
            statement.setString(3, u.getDescription());
            statement.setString(4, EncryptDecryptAES128.getInstance().encrypt(u.getPassword()));
            statement.setString(5, Integer.toString(u.getId()));
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
        String query = "UPDATE public.user SET profileimage = ? WHERE id = ?::integer";
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
