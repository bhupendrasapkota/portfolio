package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class PageView {
    private int id;
    private String pagePath;
    private String referrer;
    private String userAgent;
    private String ipAddress;
    private String country;
    private String city;
    private String deviceType;
    private LocalDateTime createdAt;

    // Default constructor
    public PageView() {}

    // Constructor with essential fields
    public PageView(String pagePath, String ipAddress) {
        this.pagePath = pagePath;
        this.ipAddress = ipAddress;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPagePath() { return pagePath; }
    public void setPagePath(String pagePath) { this.pagePath = pagePath; }

    public String getReferrer() { return referrer; }
    public void setReferrer(String referrer) { this.referrer = referrer; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "PageView{" +
                "id=" + id +
                ", pagePath='" + pagePath + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
