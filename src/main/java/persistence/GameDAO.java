package persistence;


import model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameDAO {

    private PreparedStatement statement;

    public ArrayList<Game> getUploadedGamesById(int id){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM game WHERE id_developer = ? ORDER BY id";
        ArrayList<Game> games = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                games.add(this.createSimpleGame(resultSet));
            }
            return games;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getAllGame() {
        ArrayList<Game> games = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM game";
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
                games.add(createSimpleGame(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public Game getGameByName(String name) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.game WHERE name = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return createSimpleGame(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Game createSimpleGame(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        String front_img = result.getString("front_img");
        String payment_email = result.getString("payment_email");
        String support_email = result.getString("support_email");
        double price = result.getDouble("price");
        double sale = result.getDouble("sale");
        Date release_date = result.getDate("release_date");
        int id_developer = result.getInt("id_developer");
        String description = result.getString("description");
        String specifics = result.getString("specifics");
        return new Game(id, name, front_img, payment_email, support_email, price, sale, release_date, id_developer, description, specifics, release_date);
    }

    public Game getGameById(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.game WHERE id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return createSimpleGame(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, ArrayList<Game>> getTagsAndGames(){
        HashMap<String, ArrayList<Game>> tagsAndGames = new HashMap<>();
        ArrayList<String> tags = DAOFactory.getInstance().makeTagDao().getTagsList();
        for (String tag:tags) {
            ArrayList<Game> games = DAOFactory.getInstance().makeGameDAO().getGamesFromCategory(tag);
            tagsAndGames.put(tag,games);
        }
        return tagsAndGames;
    }

    public ArrayList<Game> getGamesFromCategory(String tag) {
        ArrayList<Game> games = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT game.* FROM game, categories, tag WHERE tag.name=? AND game.id = categories.id_game AND categories.id_tag = tag.id LIMIT 10";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,tag);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Game g = new Game();
                g.setId(resultSet.getInt("id"));
                g.setName(resultSet.getString("name"));
                g.setFrontImage(resultSet.getString("front_img"));
                games.add(g);
            }
            return games;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getGamesFromNameLike(String gameName) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM game WHERE name ILIKE ?";
        gameName = "%"+gameName+"%";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            ResultSet result = statement.executeQuery();
            if (result.isClosed())
                return null;
            ArrayList<Game> games = new ArrayList<Game>();
            while (result.next()) {
                games.add(DAOFactory.getInstance().makeGameDAO().createSimpleGame(result));
            }

            return games;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertGame(String name, String frontImage, String paymentEmail, String supportEmail, double price,
                           int idDeveloper, String description, String specifics, ArrayList<String> tags,
                           ArrayList<String> externalLinks) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO game(id, name, front_img, payment_email, support_email, price, sale, release_date, " +
                "id_developer, description, specifics) VALUES(default, ?, ?, ?, ?, ?, default , default , ?, ?, ?) ";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, frontImage);
            statement.setString(3, paymentEmail);
            statement.setString(4, supportEmail);
            statement.setDouble(5, price);
            statement.setInt(6, idDeveloper);
            statement.setString(7, description);
            statement.setString(8, specifics);
            statement.executeUpdate();
            int id = this.getGameByName(name).getId();
            for(String tag : tags){
                this.insertTag(connection, tag, id);
            }
            for(String link : externalLinks){
                this.insertLink(connection, link, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isGamePurchased(int gameId, int userId) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT id FROM library WHERE id_user=? AND id_game=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            ResultSet result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertTag(Connection connection, String tag, int id_game) throws SQLException {
        String query = "INSERT INTO categories(id, id_game, id_tag) VALUES (default, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id_game);
        statement.setInt(2, DAOFactory.getInstance().makeTagDao().getIdTagByName(connection, tag));
        statement.executeUpdate();
    }

    private void insertLink(Connection connection, String link, int id_game) throws SQLException {
        String query = "INSERT INTO external_links (id, id_game, link) VALUES (default, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id_game);
        statement.setString(2, link);
        statement.executeUpdate();
    }

    public ArrayList<String> getExternalLinks(int idGame) {
        ArrayList<String> externalLinks = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT link FROM external_links WHERE id_game=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, idGame);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                externalLinks.add(resultSet.getString("link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return externalLinks;
    }

    private ArrayList<Game> getGamesFromRating(int starsNumber) {
        ArrayList<Game> games = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM average_ratings WHERE avg >= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, starsNumber);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                games.add(createSimpleGame(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    private ArrayList<Game> getGamesFromPrice(int min, int max) {
        ArrayList<Game> games = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM game WHERE price>=? AND price<?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, min);
            statement.setInt(2, max);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
                games.add(createSimpleGame(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public ArrayList<Game> getFilterGames(String gameName, String category, String price, String rating) {
        ArrayList<Game> games = getAllGame();
        System.out.println(games.toString());
        if(!gameName.equals("")){
            games.retainAll(getGamesFromNameLike(gameName));
        }
        System.out.println(games.toString());
        if(!category.equals("noCategory")){
            games.retainAll(getGamesFromCategory(category));
        }
        System.out.println(games.toString());
        if(!price.equals("noPrice")){
            String[] p = price.split("-");
            int min = Integer.parseInt(p[0]);
            int max = Integer.parseInt(p[1]);
            games.retainAll(getGamesFromPrice(min, max));
        }
        System.out.println(games.toString());
        if(!rating.equals("noRating")){
            int starsNumber = rating.charAt(rating.length() - 1);
            games.retainAll(getGamesFromRating(starsNumber));
        }
        System.out.println(games.toString());
        return games;
    }
}