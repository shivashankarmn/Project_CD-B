package com.swms.swms.controller;

import com.swms.swms.dto.LoginRequest;
import com.swms.swms.model.User;
import com.swms.swms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://13.208.189.123:3000") // Allow frontend origin

public class AuthController {

    @Autowired
    private AuthService authService;

     @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {  // Accept JSON body
        return authService.loginUser(loginRequest.getName(), 
                                     loginRequest.getPassword(), 
                                     loginRequest.getRole());
    }
}
