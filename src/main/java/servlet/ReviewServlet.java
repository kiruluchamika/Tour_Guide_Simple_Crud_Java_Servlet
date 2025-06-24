package servlet;

import model.Review;
import service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        super.init();
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

        String mode = request.getParameter("mode");
        if (mode == null) mode = "list";
        request.setAttribute("mode", mode);

        if ("edit".equals(mode) || "view".equals(mode)) {
            int reviewId = Integer.parseInt(request.getParameter("id"));
            Review review = reviewService.getReviewById(reviewId);
            request.setAttribute("currentReview", review);
        }

        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("/User/MyReviews.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/User/UserLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        String message = null;
        boolean isError = false;

        try {
            switch (action) {
                case "create":
                    Review newReview = new Review();
                    newReview.setUserId((int) session.getAttribute("userId"));
                    newReview.setPackageId(Integer.parseInt(request.getParameter("packageId")));
                    newReview.setRating(Integer.parseInt(request.getParameter("rating")));
                    newReview.setReviewText(request.getParameter("reviewText"));
                    newReview.setTitle(request.getParameter("title"));
                    newReview.setVerified(true);  // Default to verified

                    if (reviewService.createReview(newReview)) {
                        message = "Review submitted successfully";
                    } else {
                        message = "Failed to submit review";
                        isError = true;
                    }
                    break;

                case "update":
                    Review updatedReview = new Review();
                    updatedReview.setReviewId(Integer.parseInt(request.getParameter("id")));
                    updatedReview.setRating(Integer.parseInt(request.getParameter("rating")));
                    updatedReview.setReviewText(request.getParameter("reviewText"));
                    updatedReview.setTitle(request.getParameter("title"));
                    updatedReview.setVerified(Boolean.parseBoolean(request.getParameter("isVerified")));

                    if (reviewService.updateReview(updatedReview)) {
                        message = "Review updated successfully";
                    } else {
                        message = "Failed to update review";
                        isError = true;
                    }
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    if (reviewService.deleteReview(deleteId)) {
                        message = "Review deleted successfully";
                    } else {
                        message = "Failed to delete review";
                        isError = true;
                    }
                    break;
            }
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
            isError = true;
        }

        if (isError) {
            request.getSession().setAttribute("error", message);
        } else {
            request.getSession().setAttribute("success", message);
        }

        response.sendRedirect(request.getContextPath() + "/reviews");
    }
}
