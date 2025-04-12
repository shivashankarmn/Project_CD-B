package com.swms.swms.dto;

import com.swms.swms.model.User.Role;

public class LoginRequest {

    
        private String name;
        private String password;
        private Role role;
    
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public Role getRole() { return role; }  // Missing getter
        public void setRole(Role role) { this.role = role; }  // Missing setter
    }
    

