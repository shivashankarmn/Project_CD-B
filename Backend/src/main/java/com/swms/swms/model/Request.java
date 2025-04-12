package com.swms.swms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "name", nullable = false)
    private User user;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String wasteType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "assigned_driver_name", referencedColumnName = "name", nullable = true)
    private Driver assignedDriver;  // Changed from User to Driver

    public enum Status { PENDING, IN_PROGRESS, COMPLETED, CANCELLED }

    // Default Constructor
    public Request() {}

    // Parameterized Constructor
    public Request(int id, User user, String location, String wasteType, Status status, Driver assignedDriver) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.wasteType = wasteType;
        this.status = status;
        this.assignedDriver = assignedDriver;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getWasteType() { return wasteType; }
    public void setWasteType(String wasteType) { this.wasteType = wasteType; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Driver getAssignedDriver() { return assignedDriver; }  // Updated getter
    public void setAssignedDriver(Driver assignedDriver) { this.assignedDriver = assignedDriver; }  // Updated setter
}
