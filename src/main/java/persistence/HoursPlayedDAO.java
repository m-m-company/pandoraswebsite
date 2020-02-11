package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class HoursPlayedDAO {

    private PreparedStatement statement;

    public TreeMap<String, Integer> getHoursPlayedFromIdUser(int id)
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
            TreeMap<String, Integer> hours = new TreeMap<>();
            while(result.next()) {
                hours.put("'"+result.getString("name")+"'", result.getInt("value"));
            }

            return hours;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Integer> getTotalHoursPlayedByUser(int id){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT sum(value) FROM hours_played WHERE id_user = ? GROUP BY hours_played.id_game";
        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ArrayList<Integer> total = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                total.add(resultSet.getInt(1));
            }
            return total;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public TreeMap<Integer, Integer> getHoursPlayedYEARGAMEbyIdUser(int id, int idgame){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT sum(value), year FROM hours_played where id_user = ? AND id_game = ? GROUP BY hours_played.year";
        TreeMap<Integer, Integer> hashMap = new TreeMap<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.setInt(2,idgame);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                hashMap.put(resultSet.getInt("year"),resultSet.getInt(1));
            }
            return hashMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
