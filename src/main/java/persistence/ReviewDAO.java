package persistence;


import model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class ReviewDAO {

    private PreparedStatement statement;

    public Review createSimpleReview(ResultSet resultSet){
        try {
            return new Review(resultSet.getInt("id"), resultSet.getInt("id_user"), resultSet.getInt("id_game"),
                    resultSet.getInt("stars"), resultSet.getDate("date"), resultSet.getString("content"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCommentForGame(int id, int stars, String comment, int author){
        Connection connection = DbAccess.getConnection();
        String query = "INSERT INTO public.reviews(id, id_game, id_user, content, stars, date) VALUES (default,?,?,?,?,default)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, author);
            statement.setString(3, comment);
            statement.setInt(4, stars);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getAvgReviewsByIdUser(int id){
        ArrayList<Integer> averages = new ArrayList<>();
        Connection connection = DbAccess.getConnection();
        String query =
                "SELECT AVG(reviews.stars), reviews.id_game FROM reviews, game " +
                "WHERE id_developer = ? AND id_game = game.id " +
                "GROUP BY reviews.id_game ORDER BY reviews.id_game";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                averages.add(resultSet.getInt(1));
            }
            return averages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Review> getReviewsByIdGame(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.reviews WHERE id_game = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            ArrayList<Review> reviews = new ArrayList<Review>();
            while(result.next()) {
                reviews.add(createSimpleReview(result));
            }
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TreeMap<String, Integer> getReviewsTreeMapByIdGame(int id){
        Connection connection = DbAccess.getConnection();
        TreeMap<String, Integer> reviews = new TreeMap<>();
        String query =
                "SELECT date(reviews.date), avg(reviews.stars) " +
                "FROM reviews " +
                "WHERE id_game = ? " +
                "GROUP BY date(reviews.date)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                reviews.put("'"+resultSet.getDate(1)+"'", resultSet.getInt(2));
            }
            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteComment(int id) {
        Connection connection = DbAccess.getConnection();
        String query = "DELETE FROM reviews WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            if(statement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean updateComment(int idReview, String content) {
        Connection connection = DbAccess.getConnection();
        String query = "UPDATE reviews SET content=?,date=default WHERE id=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, content);
            statement.setInt(2, idReview);
            if(statement.executeUpdate() != 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
