package service;

import model.Booking;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    // Create Booking
    public boolean createBooking(Booking booking) {
        final String query = "INSERT INTO bookings (user_id, package_id, status, num_people, special_requests, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPackageId());
            stmt.setString(3, booking.getStatus());
            stmt.setInt(4, booking.getNumPeople());
            stmt.setString(5, booking.getSpecialRequests());
            stmt.setString(6, booking.getPaymentMethod());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Booking by ID
    public Booking getBookingById(int bookingId) {
        final String query = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get All Bookings (Keep if needed for admin/reporting)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        final String query = "SELECT * FROM bookings";
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Get Bookings by User ID
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        final String query = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Update Booking
    public boolean updateBooking(Booking booking) {
        final String query = "UPDATE bookings SET user_id = ?, package_id = ?, status = ?, num_people = ?, special_requests = ?, payment_method = ? WHERE booking_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPackageId());
            stmt.setString(3, booking.getStatus());
            stmt.setInt(4, booking.getNumPeople());
            stmt.setString(5, booking.getSpecialRequests());
            stmt.setString(6, booking.getPaymentMethod());
            stmt.setInt(7, booking.getBookingId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Booking
    public boolean deleteBooking(int bookingId) {
        final String query = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Count Bookings by User
    public int getBookingCountByUser(int userId) {
        final String query = "SELECT COUNT(*) FROM bookings WHERE user_id = ?";
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

    // Helper Method to Map ResultSet to Booking Object
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setPackageId(rs.getInt("package_id"));
        booking.setBookingDate(rs.getTimestamp("booking_date"));
        booking.setStatus(rs.getString("status"));
        booking.setNumPeople(rs.getInt("num_people"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setPaymentMethod(rs.getString("payment_method"));
        return booking;
    }
}
