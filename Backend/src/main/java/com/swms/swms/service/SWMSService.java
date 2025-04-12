package com.swms.swms.service;

import com.swms.swms.model.*;
import com.swms.swms.model.Driver.AvailabilityStatus;
import com.swms.swms.model.Request.Status;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface SWMSService {

    // AdminAction Methods
    AdminAction createAdminAction(AdminAction adminAction);
    AdminAction getAdminActionById(int id);
    List<AdminAction> getAllAdminActions();
    void deleteAdminAction(int id);

    // Driver Methods
    Driver addDriver(Driver driver);
    Driver getDriverById(int id);
    List<Driver> getAllDrivers();
    void deleteDriver(int id);
    Driver getDriverByName(String name);
    boolean updateAssignedDriver(int requestId, String driverName);
    String getAssignedDriverName(int requestId);

    // Method to update the availability status of the driver
    void updateDriverAvailability(String driverName, Driver.AvailabilityStatus status);  // void return type
    // Request Methods
    Request createRequest(String name, Request request);
    Request getRequestById(int id);
    List<Request> getAllRequests();
    List<Request> getRequestsByStatus(Request.Status status);
    void updateRequestStatus(int id, Status status);
    void deleteRequest(int id);
    List<Request> getRequestsByUserName(String name);
    List<Request> getRequestsByDriverUsername(String username);
//    boolean updateAssignedDriver(int requestId, String driverName);


    // User Methods
    User registerUser(User user);
    User getUserByName(String name);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    List<User> getAllUsers();
    void deleteUser(int id);
    List<String> getUsersByRole(User.Role role);



    // WasteCollectionRecord Methods
    WasteCollectionRecord createWasteCollectionRecord(WasteCollectionRecord record);
    WasteCollectionRecord getRecordById(int id);
    List<WasteCollectionRecord> getAllRecords();
    void deleteRecord(int id);
    void updateWasteCollectionStatus(int recordId, Request.Status status);

}
