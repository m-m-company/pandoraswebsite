package persistence;

import model.Score;
import utility.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ScoreDAO {
    private PreparedStatement statement;

    public ArrayList<Pair<Integer, String>> getScoresFromIdUser(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT score.score, game.name FROM public.score, public.game WHERE score.id_game = game.id and score.id_user = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,Integer.toString(id));
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            ArrayList<Pair<Integer, String>> gamesScore = new ArrayList<Pair<Integer, String>>();
            while(result.next()) {
                gamesScore.add(new Pair<Integer,String>(result.getInt("score"), result.getString("name")));
            }
            return gamesScore;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public HashMap<Integer, Integer> getDatasForGamesHoursCharts(int id){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT sum()";
        try{
            statement = connection.prepareStatement(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Score> getScoresFromIdGame(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.scoregameusername WHERE id = ? ORDER BY value DESC LIMIT 5";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            ArrayList<Score> gamesScore = new ArrayList<Score>();
            while(result.next()) {
                Score score = new Score();
                score.setIdGame(id);
                score.setValue(result.getDouble("score"));
                score.setUsername(result.getString("username"));
                gamesScore.add(score);
            }
            return gamesScore;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
