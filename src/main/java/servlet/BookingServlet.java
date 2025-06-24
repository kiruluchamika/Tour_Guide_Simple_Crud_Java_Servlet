package servlet;

import model.Booking;
import model.User;
import service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {

    private BookingService bookingService;

    @Override
    public void init() {
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        String mode = request.getParameter("mode");
        if (mode == null) mode = "list";
        request.setAttribute("mode", mode);

        if ("edit".equals(mode) || "view".equals(mode)) {
            int bookingId = Integer.parseInt(request.getParameter("id"));
            Booking currentBooking = bookingService.getBookingById(bookingId);
            request.setAttribute("currentBooking", currentBooking);
        }

        List<Booking> bookings = bookingService.getBookingsByUserId(user.getUserId());
        request.setAttribute("bookings", bookings);

        request.getRequestDispatcher("/User/MyBookings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        String message;
        boolean isError = false;

        try {
            switch (action) {
                case "create":
                    Booking newBooking = extractBookingFromRequest(request, user.getUserId());
                    newBooking.setStatus("pending");  // Default status
                    if (bookingService.createBooking(newBooking)) {
                        message = "Booking created successfully!";
                    } else {
                        message = "Failed to create booking.";
                        isError = true;
                    }
                    break;

                case "update":
                    Booking updatedBooking = extractBookingFromRequest(request, user.getUserId());
                    updatedBooking.setBookingId(Integer.parseInt(request.getParameter("id")));
                    updatedBooking.setStatus("pending");  // Keep status logic simple
                    if (bookingService.updateBooking(updatedBooking)) {
                        message = "Booking updated successfully!";
                    } else {
                        message = "Failed to update booking.";
                        isError = true;
                    }
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    if (bookingService.deleteBooking(deleteId)) {
                        message = "Booking deleted successfully!";
                    } else {
                        message = "Failed to delete booking.";
                        isError = true;
                    }
                    break;

                default:
                    message = "Unknown action.";
                    isError = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
            isError = true;
        }

        if (isError) {
            session.setAttribute("error", message);
        } else {
            session.setAttribute("success", message);
        }
        response.sendRedirect(request.getContextPath() + "/bookings");
    }

    private Booking extractBookingFromRequest(HttpServletRequest request, int userId) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPackageId(Integer.parseInt(request.getParameter("packageId")));
        booking.setNumPeople(Integer.parseInt(request.getParameter("numPeople")));
        booking.setSpecialRequests(request.getParameter("specialRequests"));
        booking.setPaymentMethod(request.getParameter("paymentMethod"));
        return booking;
    }
}
