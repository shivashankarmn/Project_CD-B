package com.swms.swms.repository;


import com.swms.swms.model.AdminAction;
import com.swms.swms.model.Driver;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByName(String name);

	

	Optional<Driver> findByIdAndName(int id, String name);
}

