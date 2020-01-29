package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class HoursPlayedDAO {

    private PreparedStatement statement;

    public HashMap<String, Integer> getHoursPlayedFromIdUser(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT *  FROM public.hours_played, game WHERE hours_played.id_game= game.id " +
                                                                "AND hours_played.id_user = ?::integer";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,Integer.toString(id));
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            HashMap<String, Integer> hours = new HashMap<>();
            while(result.next()) {
                hours.put(result.getString("name"), result.getInt("value"));
            }

            return hours;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
