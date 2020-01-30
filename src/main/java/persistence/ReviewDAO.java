package persistence;


import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {
    private PreparedStatement statement;

    public Review createSimpleReview(ResultSet resultSet){
        try {
            return new Review(resultSet.getInt("id_user"), resultSet.getInt("id_game"),
                    resultSet.getInt("stars"), resultSet.getDate("date"), resultSet.getString("content"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCommentForGame(int id, int stars, String comment, int author, String username){
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

    public ArrayList<Review> getReviewsByIdGame(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.reviewuser WHERE id_game = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,Integer.toString(id));
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

    public ArrayList<Review> getReviewByIdUser(int id){
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM reviews WHERE id_user = ?";
        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Review> reviews = new ArrayList<>();
            while (resultSet.next()){
                reviews.add(createSimpleReview(resultSet));
            }
            return reviews;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
