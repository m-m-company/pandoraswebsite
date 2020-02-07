package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TagDAO {

    private PreparedStatement statement;

    public int getIdTagByName(Connection connection, String name){
        if(connection == null){
            connection = DbAccess.getConnection();
        }
        String query = "SELECT * FROM tag WHERE name = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getTagsList() {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT tag.name FROM tag";
        ArrayList<String> tags = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                tags.add(resultSet.getString(1));
            }
            return tags;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
