package com.swms.swms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "waste_collection_records")
public class WasteCollectionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @Column(nullable = false)
    private LocalDateTime collectionTime;

    private String confirmationPhoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Request.Status status; // NEWLY ADDED FIELD

    // Default Constructor
    public WasteCollectionRecord() {}

    // Parameterized Constructor
    public WasteCollectionRecord(int id, Request request, User driver, LocalDateTime collectionTime, String confirmationPhoto, Request.Status status) {
        this.id = id;
        this.request = request;
        this.driver = driver;
        this.collectionTime = collectionTime;
        this.confirmationPhoto = confirmationPhoto;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Request getRequest() { return request; }
    public void setRequest(Request request) { this.request = request; }

    public User getDriver() { return driver; }
    public void setDriver(User driver) { this.driver = driver; }

    public LocalDateTime getCollectionTime() { return collectionTime; }
    public void setCollectionTime(LocalDateTime collectionTime) { this.collectionTime = collectionTime; }

    public String getConfirmationPhoto() { return confirmationPhoto; }
    public void setConfirmationPhoto(String confirmationPhoto) { this.confirmationPhoto = confirmationPhoto; }

    public Request.Status getStatus() { return status; }
    public void setStatus(Request.Status status) { this.status = status; }
}
