package com.swms.swms.service;

import com.swms.swms.exception.ResourceNotFoundException;
import com.swms.swms.model.*;
import com.swms.swms.model.Driver.AvailabilityStatus;
import com.swms.swms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SWMSServiceImpl implements SWMSService {

    @Autowired
    private AdminActionRepository adminActionRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WasteCollectionRecordRepository wasteCollectionRecordRepository;

    // ------------------- AdminAction Methods -------------------
    @Override
    public AdminAction createAdminAction(AdminAction adminAction) {
        return adminActionRepository.save(adminAction);
    }

    @Override
    public AdminAction getAdminActionById(int id) {
        return adminActionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin Action not found with ID: " + id));
    }

    @Override
    public List<AdminAction> getAllAdminActions() {
        return adminActionRepository.findAll();
    }

    @Override
    public void deleteAdminAction(int id) {
        if (!adminActionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin Action not found with ID: " + id);
        }
        adminActionRepository.deleteById(id);
    }

    // ------------------- Driver Methods -------------------
    @Override
    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver getDriverById(int id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with ID: " + id));
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public void deleteDriver(int id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found with ID: " + id);
        }
        driverRepository.deleteById(id);
    }

    // ------------------- Request Methods -------------------
    
    @Override
    public Request createRequest(String name, Request request) {
        User user = userRepository.findByName(name) // Change if username is stored differently
                .orElseThrow(() -> new RuntimeException("User not found"));

        request.setUser(user); // Assign user to the request
        return requestRepository.save(request);
    }



    @Override
    public Request getRequestById(int id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with ID: " + id));
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> getRequestsByStatus(Request.Status status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public void updateRequestStatus(int id, Request.Status status) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with ID: " + id));
        request.setStatus(status);
        requestRepository.save(request);
    }
    

    @Override
    public void deleteRequest(int id) {
        if (!requestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Request not found with ID: " + id);
        }
        requestRepository.deleteById(id);
    }

    // ------------------- User Methods -------------------
    @Override
public User registerUser(User user) {
    User savedUser = userRepository.save(user);
    
    // If the user is a driver, create a Driver entry
//    if (user.getRole() == User.Role.DRIVER) {
//        Driver driver = new Driver();
//        driver.setName(user.getName());
//        driver.setUser(savedUser);
//        driverRepository.save(driver);
//    }

    return savedUser;
}


    @Override
public User getUserByName(String name) {
    return userRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with Name: " + name));
}

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Email: " + email));
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Phone: " + phone));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
    @Override
public List<Request> getRequestsByUserName(String name) {
    User user = userRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + name));

    return requestRepository.findByUser_Name(user.getName()); // ✅ Ensure `user.getName()` is used
}

    

    // ------------------- WasteCollectionRecord Methods -------------------
    @Override
    public WasteCollectionRecord createWasteCollectionRecord(WasteCollectionRecord record) {
        return wasteCollectionRecordRepository.save(record);
    }

    @Override
    public WasteCollectionRecord getRecordById(int id) {
        return wasteCollectionRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waste Collection Record not found with ID: " + id));
    }

    @Override
    public List<WasteCollectionRecord> getAllRecords() {
        return wasteCollectionRecordRepository.findAll();
    }

    @Override
    public void deleteRecord(int id) {
        if (!wasteCollectionRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Waste Collection Record not found with ID: " + id);
        }
        wasteCollectionRecordRepository.deleteById(id);
    }

    @Override
public void updateWasteCollectionStatus(int recordId, Request.Status status) {
    WasteCollectionRecord record = wasteCollectionRecordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Waste Collection Record not found with ID: " + recordId));

    // Update WasteCollectionRecord status
    record.setStatus(status);
    wasteCollectionRecordRepository.save(record);

    // Update corresponding Request status (Only Admin should be allowed to confirm "COMPLETED")
    if (status == Request.Status.COMPLETED) {
        Request request = record.getRequest();
        request.setStatus(Request.Status.COMPLETED);
        requestRepository.save(request);
    }
}

@Override
public Driver getDriverByName(String name) {
    return driverRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Driver not found with Name: " + name));
}


@Override
public List<Request> getRequestsByDriverUsername(String username) {
    return requestRepository.findByAssignedDriver_Name(username);
}

@Override
public List<String> getUsersByRole(User.Role role) {
    // Check if the role is DRIVER
    if (role == User.Role.DRIVER) {
        // Fetch users with the DRIVER role
        List<User> users = userRepository.findByRole(role);
        
        // Map the users to a list of strings in the format "id:name"
        return users.stream()
                    .map(user -> user.getId() + ":" + user.getName()) // Concatenate id and name
                    .collect(Collectors.toList());
    } else {
        return List.of(); // Return an empty list if the role is not DRIVER
    }
}


@Override
public boolean updateAssignedDriver(int requestId, String driverName) {
    // Fetch the Request by id
    Request request = requestRepository.findById(requestId).orElse(null);
    if (request == null) {
        return false;  // Request not found
    }

    // Fetch the Driver by name
    Optional<Driver> driverOptional = driverRepository.findByName(driverName);
    if (driverOptional.isEmpty()) {
        return false;  // Driver not found
    }

    Driver driver = driverOptional.get();  // Unwrap the Optional

    // Assign the driver to the request
    request.setAssignedDriver(driver);
    requestRepository.save(request);  // Save the updated Request

    return true;
}

@Override
public String getAssignedDriverName(int requestId) {
	 // Fetch the request by ID and retrieve the assigned driver's name
    Request request = requestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Request not found with ID: " + requestId));

    // Return the assigned driver's name or null if no driver is assigned
    return request.getAssignedDriver() != null ? request.getAssignedDriver().getName() : null;
}

@Override
public void updateDriverAvailability(String driverName, AvailabilityStatus status) {
	// Fetch the driver by name and update their availability status
    Driver driver = driverRepository.findByName(driverName)
            .orElseThrow(() -> new ResourceNotFoundException("Driver not found with name: " + driverName));

    driver.setAvailabilityStatus(status);
    driverRepository.save(driver);
}



//@Override
//public boolean updateAssignedDriver(int requestId, String driverName) {
//    // Fetch the Request by id
//    Request request = requestRepository.findById(requestId).orElse(null);
//    if (request == null) {
//        return false;  // Request not found
//    }
//
//    // Fetch the Driver by name
//    Optional<Driver> driverOptional = driverRepository.findByName(driverName);
//    if (driverOptional.isEmpty()) {
//        return false;  // Driver not found
//    }
//
//    Driver driver = driverOptional.get();  // Unwrap the Optional
//
//    // Update the assignedDriver
//    request.setAssignedDriver(driver);
//    requestRepository.save(request);  // Save the updated Request
//
//    return true;
//}
//
//@Override
//public void updateDriverAvailabilityStatus(int id, String name, Driver.AvailabilityStatus status) {
//    // Fetch the driver using the id and name
//    Optional<Driver> driverOptional = driverRepository.findByIdAndName(id, name);
//    if (driverOptional.isPresent()) {
//        Driver driver = driverOptional.get();
//        driver.setAvailabilityStatus(status); // Update the availability status
//        driverRepository.save(driver); // Save the updated driver to the database
//    } else {
//        throw new ResourceNotFoundException("Driver with id " + id + " and name " + name + " not found");
//    }
//}
	 
}


