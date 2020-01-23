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
        String query = "INSERT INTO public.review(idreview, stars, comment, author, game, username) VALUES (default,?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, stars);
            statement.setString(2, comment);
            statement.setInt(3, author);
            statement.setInt(4, id);
            statement.setString(5, username);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Review> getReviewsFromIdGame(int id)
    {
        Connection connection = DbAccess.getConnection();
        String query = "SELECT * FROM public.review WHERE game = ?::integer";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,Integer.toString(id));
            ResultSet result = statement.executeQuery();
            if(result.isClosed())
                return null;
            ArrayList<Review> reviews = new ArrayList<Review>();
            while(result.next()) {
                Review review = new Review();
                review.setIdGame(id);
                review.setUsername(result.getString("username"));
                review.setAuthor(result.getInt("author"));
                review.setComment(result.getString("comment"));
                review.setStars(result.getInt("stars"));
                reviews.add(review);
            }

            return reviews;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
