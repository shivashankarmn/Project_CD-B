package com.swms.swms.repository;

import com.swms.swms.model.User;
import com.swms.swms.model.User.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByName(String name);
    List<User> findByRole(User.Role role);
    

}
