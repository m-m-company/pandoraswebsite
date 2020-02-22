
package persistence;

import model.Game;
import model.SoldGames;
import utility.Acquisto;
import utility.Pair;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PurchaseDAO {

    private PreparedStatement statement;

    public TreeMap<Integer,Integer> getGamesYearFromIdUser(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.purchase WHERE purchase.id_user = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,Integer.toString(id));
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            TreeMap<Integer,Integer> gamesPlayed = new TreeMap<Integer,Integer>();
            ArrayList<Pair<Integer, Integer>> yearGames = new ArrayList<Pair<Integer, Integer>>();
            Set<Integer> years = new TreeSet<Integer>();
            Calendar calendar = Calendar.getInstance();
            while(result.next()) {
                calendar.setTime(result.getDate("date"));
                Pair<Integer, Integer> pair = new Pair<Integer,Integer>(calendar.get(Calendar.YEAR),result.getInt("id_game"));
                yearGames.add(pair);
                years.add(calendar.get(Calendar.YEAR));
            }
            for(Integer year : years)
            {
                int contGames = 0;
                for (Pair<Integer,Integer> pair: yearGames)
                {
                    if(pair.getFirst().equals(year))
                        contGames++;
                }
                gamesPlayed.put(year, contGames);
            }

            return gamesPlayed;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public TreeMap<Date, Integer>

    public ArrayList<Game> getBestSellers() {
        ArrayList<Game> games = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT game.*, count(purchase.id) as p FROM purchase, game WHERE game.id = purchase.id_game GROUP BY game.id ORDER BY p LIMIT 3";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                Game g = new Game();
                g.setName(resultSet.getString("name"));
                g.setFrontImage(resultSet.getString("front_img"));
                g.setId(resultSet.getInt("id"));
                games.add(g);
            }
            return games;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertNewPurchase(Acquisto acquisto) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.purchase(id, id_user, id_game, price, date) VALUES (default,?,?,?,default)";
        try {
            Date date = new Date(100);
            statement = connection.prepareStatement(query);
            statement.setInt(1, acquisto.getIdUser());
            statement.setInt(2, acquisto.getIdGame());
            statement.setDouble(3, acquisto.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalSellsByIdUser(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT COUNT(*) FROM purchase, game WHERE id_game = game.id AND game.id_developer = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public TreeMap<String, Integer> getSellsByIdGame(int id){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT COUNT(*), purchase.date FROM purchase WHERE id_game = ? GROUP BY date";
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                treeMap.put("'" + resultSet.getDate(2) + "'", resultSet.getInt(1));
            }
            return treeMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<? extends Integer> getSellsByIdUser(int id) {
        Connection connection = DbAccess.getConnection();
        ArrayList<Integer> sells = new ArrayList<>();
        String query = "SELECT COUNT(*), game.id FROM purchase, game WHERE id_game = game.id AND game.id_developer = ? GROUP BY game.id ORDER BY game.id";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                sells.add(resultSet.getInt(1));
            }
            return sells;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getEarningsByIdUser(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT sum(purchase.price) FROM purchase, game WHERE purchase.id_game=game.id AND game.id_developer = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public TreeMap<String, Double> getPricesByIdGame(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT AVG(purchase.price), purchase.date FROM purchase WHERE id_game = ? GROUP BY date";
        TreeMap<String, Double> treeMap = new TreeMap<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                treeMap.put("'" + resultSet.getDate(2) + "'", resultSet.getDouble(1));
            }
            return treeMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}