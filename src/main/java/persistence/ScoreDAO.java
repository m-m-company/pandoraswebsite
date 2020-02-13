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

    public TreeMap<String, Integer> getScoresByIdUserForGameId(int user, int game){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT AVG(score.score), date(score.date) FROM score WHERE id_user = ? AND id_game = ? GROUP BY date(score.date)";
        TreeMap<String, Integer> scores = new TreeMap<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,user);
            statement.setInt(2,game);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                scores.put("'"+resultSet.getDate(2)+"'", resultSet.getInt(1));
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Score> getScoresFromIdGame(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.scoregameusername WHERE id = ? ORDER BY public.scoregameusername.score DESC LIMIT 5";
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

    public Integer getBestScoreIdUserGame(int user, int game){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT score.score FROM score WHERE id_user=? AND id_game=? ORDER BY score DESC LIMIT 1";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            statement.setInt(2, game);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
