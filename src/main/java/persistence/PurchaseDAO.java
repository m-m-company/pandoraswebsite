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

    public SoldGames getSoldGamesFromIdUser(int id)
    {
        //FIXME: Se hai buona volontà cerca di capirci qualcosa tu, io non riesco.
        Connection connection = DbAccess.getConnection();
        TreeMap<Integer,Integer> soldGPerYear = new TreeMap<Integer,Integer>();
        TreeMap<Integer,Double> earnedMoneyPerYear = new TreeMap<Integer,Double>();
        String queryYears = "SELECT EXTRACT(YEAR FROM date) FROM public.purchase;";
        try
        {
            statement = connection.prepareStatement(queryYears);
            ResultSet result = statement.executeQuery();
            while(result.next())
            {
                soldGPerYear.put(Integer.valueOf(result.getString(1)), 0);
                earnedMoneyPerYear.put(Integer.valueOf(result.getString(1)), 0.0);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        String querySoldGames = "SELECT EXTRACT(YEAR FROM date), id_game FROM public.purchase;";
        try
        {
            statement = connection.prepareStatement(querySoldGames);
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            SoldGames soldGames = new SoldGames();
            int idGame = 0;
            double currentPrice = 0;
            while(result.next())
            {
                idGame = result.getInt("game");
                String currentYear = result.getString(1);
                String queryGame = "SELECT * FROM public.game WHERE id = " + idGame + " AND id_developer = " + id + ";";
                PreparedStatement statementGame = connection.prepareStatement(queryGame);
                ResultSet resultGame = statementGame.executeQuery();
                if(resultGame.next())
                {
                    soldGPerYear.put(Integer.valueOf(currentYear), soldGPerYear.get(Integer.valueOf(currentYear)) + 1);
                    currentPrice += resultGame.getDouble("price");
                    earnedMoneyPerYear.put(Integer.valueOf(currentYear), earnedMoneyPerYear.get(Integer.valueOf(currentYear)) + currentPrice);
                }
            }
            soldGames.setSoldGPerYear(soldGPerYear);
            soldGames.setEarnedMoneyPerYear(earnedMoneyPerYear);
            return soldGames;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getBestThreeSoldGames()
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT previewimg.link, previewimg.game FROM public.previewimg WHERE previewimg.front = true and previewimg.game IN (SELECT purchase.game FROM public.purchase GROUP BY purchase.game ORDER BY count(*) DESC LIMIT 3);";
        try
        {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            ArrayList<Game> games = new ArrayList<Game>();
            while(result.next())
            {
                Game game = new Game();
                game.setId(result.getInt("game"));
                game.setFrontImage(result.getString("link"));
                games.add(game);
            }
            return games;
        } catch (SQLException e)
        {
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

}
