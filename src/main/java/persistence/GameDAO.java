package persistence;


import model.Game;
import model.Review;

import java.sql.*;
import java.util.ArrayList;

public class GameDAO {

    private PreparedStatement statement;

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
        return new Game(id, name, front_img, payment_email, support_email, price, sale, release_date, id_developer, description, specifics);
    }

    public Game getGameById(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.game WHERE idgame = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(id));
            ResultSet result = statement.executeQuery();
            if (result.isClosed())
                return null;
            Game game = new Game();
            while (result.next()) {
                game.setId(id);
                createSimpleGame(result);
            }
            //game.setReviews(DAOFactory.getInstance().makeReviewDAO().getReviewsFromIdGame(game.getId()));
            return game;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Review> getReviews(Game g) {
        Connection connection = DbAccess.getConnection();
        ArrayList<Review> reviews = new ArrayList<>();
        String query = "select r.* from public.reviews as r, public.game as g WHERE r.id_game = g.id AND g.id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, g.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reviews.add(DAOFactory.getInstance().makeReviewDAO().createSimpleReview(resultSet));
            }
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * if true selects only the front preview image
     **/
    public ArrayList<Game> getAllGamesFromCategory(String category) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT game.idgame, previewimg.link FROM public.game, public.previewimg WHERE game.idgame = previewimg.game and previewimg.front = true and game.category = ?;";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, category);
            ResultSet result = statement.executeQuery();
            if (result.isClosed())
                return null;
            ArrayList<Game> gamesCategory = new ArrayList<Game>();
            while (result.next()) {
                Game game = new Game();
                game.setId(result.getInt("idgame"));
                game.setFrontImage(result.getString("link"));
                gamesCategory.add(game);
            }

            return gamesCategory;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Game> getGamesFromNameLike(String gameName) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT game.idgame, previewimg.link FROM public.game, public.previewimg WHERE game.idgame = previewimg.game and previewimg.front = true and game.name like ?;";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + gameName + "%");
            ResultSet result = statement.executeQuery();
            if (result.isClosed())
                return null;
            ArrayList<Game> games = new ArrayList<Game>();
            while (result.next()) {
                Game game = new Game();
                game.setId(result.getInt("idgame"));
                game.setFrontImage(result.getString("link"));
                games.add(game);
            }

            return games;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertGame(String name, String frontImage, String paymentEmail, String supportEmail, double price, int idDeveloper, String description, String specifics, ArrayList<String> tags, ArrayList<String> externalLinks) throws SQLException {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO game(id, name, front_img, payment_email, support_email, price, sale, release_date, id_developer, description, specifics) " +
                "VALUES(default, ?, ?, ?, ?, ?, default , default , ?, ?, ?) ";
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

    public void insertPreview(int id, String imageName, boolean front) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.previewimg(idpreview, link, game, front) VALUES(default,?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, imageName);
            statement.setInt(2, id);
            statement.setBoolean(3, front);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVideoLink(int id, String link) {
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.previewvid(idpreview, link, game) VALUES(default,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, link);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNewGameIntoLibrary(int game, int user) {
        Connection connection = DbAccess.getConnection();
        int nextId = getLibraryNextId(connection);
        String query = "INSERT INTO public.libreria values(?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            statement.setInt(2, game);
            statement.setInt(3, nextId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLibraryNextId(Connection conn) {
        String query = "SELECT nextval('libreria_sequence') AS id";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(query);
            ResultSet set = stmt.executeQuery();
            set.next();
            return set.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isGamePurchased(int gameId, int userId) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT idgame FROM public.libreria WHERE iduser = ? and idgame = ?;";
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

}
