package service;

import model.Package;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageService {

    // Get All Packages
    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM Packages";

        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                packages.add(mapResultSetToPackage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packages;
    }

    // Helper: Map ResultSet to Package Object
    private Package mapResultSetToPackage(ResultSet rs) throws SQLException {
        Package pkg = new Package();
        pkg.setPackageId(rs.getInt("package_id"));
        pkg.setPackageName(rs.getString("package_name"));
        pkg.setDescription(rs.getString("description"));
        pkg.setPrice(rs.getDouble("price"));
        pkg.setDuration(rs.getInt("duration"));
        pkg.setLocation(rs.getString("location"));
        pkg.setPackageType(rs.getString("package_type"));
        pkg.setAvailableFrom(rs.getDate("available_from"));
        pkg.setAvailableUntil(rs.getDate("available_until"));
        return pkg;
    }
    
    public int getPackageCount() {
        String query = "SELECT COUNT(*) FROM Packages";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
