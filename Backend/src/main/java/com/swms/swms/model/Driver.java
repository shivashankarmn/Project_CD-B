package com.swms.swms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers", indexes = @Index(name = "idx_driver_name", columnList = "name"))
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true, unique = true)
    private User user;

    @Column(nullable = false)
    private String name; // 👈 Store the driver's name here

    @Column(nullable = false)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availabilityStatus;

    public enum AvailabilityStatus { AVAILABLE, BUSY, INACTIVE }

    // Default Constructor
    public Driver() {}

    public Driver(User user, String name, String vehicleNumber, AvailabilityStatus availabilityStatus) {
        this.user = user;
        this.name = name;
        this.vehicleNumber = vehicleNumber;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
