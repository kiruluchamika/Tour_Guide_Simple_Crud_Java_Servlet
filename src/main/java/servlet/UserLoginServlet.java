package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");  // Assuming password handling, even if not in DB yet

        // Check if session already exists
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/user/dashboard");
            return;
        }

        // Authenticate user
        User user = userService.login(email, password);

        if (user != null) {
            // Create a new session for the authenticated user
            session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("fullName", user.getFirstName() + " " + user.getLastName());
            session.setMaxInactiveInterval(30 * 60); // Session expires after 30 minutes

            // Redirect to the user dashboard
            response.sendRedirect(request.getContextPath() + "/user/dashboard");
        } else {
            // Authentication failed
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/User/dashboard").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for existing session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Redirect authenticated user to dashboard
            response.sendRedirect(request.getContextPath() + "/user/dashboard");
        } else {
            // Show login page
            request.getRequestDispatcher("/User/UserLogin.jsp").forward(request, response);
        }
    }
}
