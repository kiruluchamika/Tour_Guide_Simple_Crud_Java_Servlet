package servlet;

import model.Package;
import service.PackageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/packages")
public class PackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PackageService packageService;

    @Override
    public void init() throws ServletException {
        packageService = new PackageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Package> packages = packageService.getAllPackages();
        request.setAttribute("packages", packages);

        request.getRequestDispatcher("/User/ViewPackages.jsp").forward(request, response);
    }
}
