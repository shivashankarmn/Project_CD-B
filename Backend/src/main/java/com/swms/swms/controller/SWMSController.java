package com.swms.swms.controller;

import com.swms.swms.exception.ResourceNotFoundException;
import com.swms.swms.model.*;
import com.swms.swms.service.SWMSService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://13.208.189.123:3000") 

public class SWMSController {
    
    private final SWMSService swmsService;

    public SWMSController(SWMSService swmsService) {
        this.swmsService = swmsService;
    }
    //------------USED ENDPOINTS-------------
    
    //adding drivers
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adddrivers")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        return new ResponseEntity<>(swmsService.addDriver(driver), HttpStatus.CREATED);
    }
    
    //get all requests
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requests")
    public ResponseEntity<List<Request>> getAllRequests() {
        return new ResponseEntity<>(swmsService.getAllRequests(), HttpStatus.OK);
    }
    
    //get users by role
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<String>> getUsersByRole(@PathVariable User.Role role) {
        List<String> users = swmsService.getUsersByRole(role);  // Fetch names only
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No users found
        }
        return new ResponseEntity<>(users, HttpStatus.OK); // Return only names
    }
    
    //update the availability status of the driver
    @PutMapping("/drivers/{requestId}/update-status/{status}")
    public ResponseEntity<String> updateRequestStatus(@PathVariable("requestId") int requestId,
                                                      @PathVariable("status") String status) {
        Request.Status requestStatus;
        try {
            requestStatus = Request.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid status value provided");
        }
        String assignedDriver = swmsService.getAssignedDriverName(requestId);
        if (Request.Status.COMPLETED.equals(requestStatus) && assignedDriver != null) {
            swmsService.updateDriverAvailability(assignedDriver, Driver.AvailabilityStatus.AVAILABLE);  
            return ResponseEntity.ok("Request status updated to COMPLETED, and driver's availability set to AVAILABLE");
        }
        return ResponseEntity.ok("Request status updated");
    }
    
    //update the status of the request
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    @PutMapping("/requests/{id}/status")
    public ResponseEntity<Void> updateRequestStatus(@PathVariable int id, @RequestBody Request request) {
        swmsService.updateRequestStatus(id, request.getStatus());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    //get all drivers
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return new ResponseEntity<>(swmsService.getAllDrivers(), HttpStatus.OK);
    }
    
    //assign driver to the request
    @PutMapping("/requests/{requestId}/assign-driver/{driverName}")
    public ResponseEntity<String> assignDriverToRequest(@PathVariable("requestId") int requestId,
                                                         @PathVariable("driverName") String driverName) {
        boolean updated = swmsService.updateAssignedDriver(requestId, driverName);
        if (updated) {
            swmsService.updateDriverAvailability(driverName, Driver.AvailabilityStatus.BUSY);  // Update driver's availability status to BUSY
            return ResponseEntity.ok("Driver assigned successfully and availability updated to BUSY");
        } else {
            return ResponseEntity.status(400).body("Failed to assign driver");
        }
    }
    
    //delete the driver by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable int id) {
        swmsService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    //create the request by users
    @PreAuthorize("hasRole('USER')")
    @PostMapping("users/requests/{username}")
    public ResponseEntity<Request> createRequest(@PathVariable String username, @RequestBody Request request) {
        return new ResponseEntity<>(swmsService.createRequest(username, request), HttpStatus.CREATED);
    }
    
    //delete the request
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
        swmsService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
   
    //register the users
    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(swmsService.registerUser(user), HttpStatus.CREATED);
    }
    
    //get request by username
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/requests/user/name/{name}")
    public ResponseEntity<List<Request>> getRequestsByUserName(@PathVariable String name) {
        return new ResponseEntity<>(swmsService.getRequestsByUserName(name), HttpStatus.OK);
    }
    
    //get request by drivername
    @GetMapping("/driver/{username}")
    public List<Request> getRequestsByDriverUsername(@PathVariable String username) {
        return swmsService.getRequestsByDriverUsername(username);
    }
   
    // ---------------- Driver Endpoints ----------------
//    @PreAuthorize("hasRole('ADMIN') or hasRole('DRIVER')")
//    @GetMapping("/drivers/name/{name}")
//    public ResponseEntity<Driver> getDriverByName(@PathVariable String name) {
//        return new ResponseEntity<>(swmsService.getDriverByName(name), HttpStatus.OK);
//    }
//    
//

    // ---------------- Request Endpoints ----------------
 
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
//    @GetMapping("/requests/{id}")
//    public ResponseEntity<Request> getRequestById(@PathVariable int id) {
//        return new ResponseEntity<>(swmsService.getRequestById(id), HttpStatus.OK);
//    }

   
    // ---------------- User Endpoints ----------------
   
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return new ResponseEntity<>(swmsService.getAllUsers(), HttpStatus.OK);
//    }
//

//    @PreAuthorize("hasRole('Admin')")
//    @GetMapping("/users/name/{name}")
//    public ResponseEntity<User> getUserByName(@PathVariable String name) {
//        return new ResponseEntity<>(swmsService.getUserByName(name), HttpStatus.OK);
//    }
//    
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
//        swmsService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    ---------------- Waste Collection Record Endpoints ----------------
//    @PreAuthorize("hasRole('DRIVER')")
//    @PostMapping("/waste-collection")
//    public ResponseEntity<WasteCollectionRecord> createWasteCollectionRecord(@RequestBody WasteCollectionRecord record) {
//        return new ResponseEntity<>(swmsService.createWasteCollectionRecord(record), HttpStatus.CREATED);
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
//    @GetMapping("/waste-collection/{id}")
//    public ResponseEntity<WasteCollectionRecord> getRecordById(@PathVariable int id) {
//        return new ResponseEntity<>(swmsService.getRecordById(id), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/waste-collection")
//    public ResponseEntity<List<WasteCollectionRecord>> getAllRecords() {
//        return new ResponseEntity<>(swmsService.getAllRecords(), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/waste-collection/{id}")
//    public ResponseEntity<Void> deleteRecord(@PathVariable int id) {
//        swmsService.deleteRecord(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//    @PreAuthorize("hasRole('DRIVER')")
//@PutMapping("/waste-collection/{recordId}/status")
//public ResponseEntity<Void> updateWasteCollectionStatus(@PathVariable int recordId, @RequestParam Request.Status status) {
//    swmsService.updateWasteCollectionStatus(recordId, status);
//    return ResponseEntity.ok().build();
//}
    

    
   }


   


