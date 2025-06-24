package model;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int userId;
    private int packageId;
    private int rating;
    private String reviewText;
    private Timestamp reviewDate;
    private String title;
    private boolean isVerified;

    // Getters
    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVerified() {
        return isVerified;
    }

    // Setters
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
}
