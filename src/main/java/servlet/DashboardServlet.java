package servlet;

import service.PackageService;
import service.BookingService;
import service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PackageService packageService;
    private BookingService bookingService;
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        packageService = new PackageService();
        bookingService = new BookingService();
        reviewService = new ReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        int packageCount = packageService.getPackageCount();
        int bookingCount = bookingService.getBookingCountByUser(userId);
        int reviewCount = reviewService.getReviewCountByUser(userId);

        request.setAttribute("packageCount", packageCount);
        request.setAttribute("bookingCount", bookingCount);
        request.setAttribute("reviewCount", reviewCount);

        request.getRequestDispatcher("/User/UserDashboard.jsp").forward(request, response);
    }
}
