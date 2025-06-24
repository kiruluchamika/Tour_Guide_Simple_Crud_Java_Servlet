package model;

import java.sql.Date;

public class Package {
    private int packageId;
    private String packageName;
    private String description;
    private double price;
    private int duration;
    private String location;
    private String packageType;
    private Date availableFrom;
    private Date availableUntil;

    // Getters
    public int getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getPackageType() {
        return packageType;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public Date getAvailableUntil() {
        return availableUntil;
    }

    // Setters
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public void setAvailableUntil(Date availableUntil) {
        this.availableUntil = availableUntil;
    }
}
