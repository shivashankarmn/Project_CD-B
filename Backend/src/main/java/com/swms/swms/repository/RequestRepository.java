package com.swms.swms.repository;

import com.swms.swms.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByStatus(Request.Status status);
    List<Request> findByUser_Name(String name);
    List<Request> findByAssignedDriver_Name(String driverName);
    Optional<Request> findById(Long id);

}
