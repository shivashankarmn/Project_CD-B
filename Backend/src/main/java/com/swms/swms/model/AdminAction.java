package com.swms.swms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_actions")
public class AdminAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @OneToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;

    public enum ActionType { ASSIGN_DRIVER, STATUS_UPDATE, CANCEL_REQUEST }

    // Default Constructor
    public AdminAction() {}

    // Parameterized Constructor
    public AdminAction(int id, User admin, Request request, ActionType actionType) {
        this.id = id;
        this.admin = admin;
        this.request = request;
        this.actionType = actionType;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getAdmin() { return admin; }
    public void setAdmin(User admin) { this.admin = admin; }

    public Request getRequest() { return request; }
    public void setRequest(Request request) { this.request = request; }

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
}


