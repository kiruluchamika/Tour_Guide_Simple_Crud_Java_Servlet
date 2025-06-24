package service;

import model.Review;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    // Create Review
    public boolean createReview(Review review) {
        String query = "INSERT INTO reviews (user_id, package_id, rating, review_text, title, is_verified) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getPackageId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getReviewText());
            stmt.setString(5, review.getTitle());
            stmt.setBoolean(6, review.isVerified());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Review by ID
    public Review getReviewById(int reviewId) {
        String query = "SELECT * FROM reviews WHERE review_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, reviewId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToReview(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get All Reviews by User
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reviews.add(mapResultSetToReview(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Update Review
    public boolean updateReview(Review review) {
        String query = "UPDATE reviews SET rating = ?, review_text = ?, title = ?, is_verified = ? WHERE review_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, review.getRating());
            stmt.setString(2, review.getReviewText());
            stmt.setString(3, review.getTitle());
            stmt.setBoolean(4, review.isVerified());
            stmt.setInt(5, review.getReviewId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete Review
    public boolean deleteReview(int reviewId) {
        String query = "DELETE FROM reviews WHERE review_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper Method: Map ResultSet to Review Object
    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setPackageId(rs.getInt("package_id"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("review_text"));
        review.setReviewDate(rs.getTimestamp("review_date"));
        review.setTitle(rs.getString("title"));
        review.setVerified(rs.getBoolean("is_verified"));
        return review;
    }
    
    public int getReviewCountByUser(int userId) {
        String query = "SELECT COUNT(*) FROM Reviews WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
