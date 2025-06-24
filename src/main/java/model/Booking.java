package model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private int packageId;
    private Timestamp bookingDate;
    private String status;
    private int numPeople;
    private String specialRequests;
    private String paymentMethod;

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPackageId() {
        return packageId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Setters
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
