package com.swms.swms.service;

import com.swms.swms.model.User;
import com.swms.swms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String loginUser(String name, String password, User.Role role) { 
        // Handle admin login with plain-text password
        if ("admin".equals(name)) {
            if ("admin123".equals(password) && role.equals(User.Role.ADMIN)) {
                return "Login successful! Welcome Admin (" + role + ")";
            }
            return "Invalid admin credentials or role!";
        }

        // Fetch user from the database for other users
        Optional<User> userOptional = userRepository.findByName(name);

        // Validate if the user exists
        if (userOptional.isEmpty()) {
            return "User not found!";
        }

        User user = userOptional.get();

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password!";
        }

        // Validate role
        if (!user.getRole().equals(role)) {
            return "Incorrect role selected! Expected: " + user.getRole();
        }

        return "Login successful! Welcome " + user.getName() + " (" + user.getRole() + ")";
    }

}





// package com.swms.swms.service;

// import com.swms.swms.model.User;
// import com.swms.swms.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import java.util.Optional;

// @Service
// public class AuthService {

//     @Autowired
//     private UserRepository userRepository;

//     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public String loginUser(String email, String password, User.Role role) { 
//         Optional<User> userOptional = userRepository.findByEmail(email);

//         if (userOptional.isEmpty()) {
//             return "User not found!";
//         }

//         User user = userOptional.get();

//         if (!passwordEncoder.matches(password, user.getPassword())) {
//             return "Invalid password!";
//         }

//         // Compare the enum role directly
//         if (user.getRole() != role) { 
//             return "Incorrect role selected!";
//         }

//         return "Login successful! Welcome " + user.getName() + " (" + role + ")";
//     }
// }





// package com.swms.swms.service;

// import com.swms.swms.model.User;
// import com.swms.swms.model.User.Role;
// import com.swms.swms.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import java.util.Optional;

// @Service
// public class AuthService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;  // Injected from SecurityConfig

//     public String loginUser(String email, String password, Role role) {
//         Optional<User> userOptional = userRepository.findByEmail(email);

//         if (userOptional.isEmpty()) {
//             return "User not found!";
//         }

//         User user = userOptional.get();

//         // Validate password
//         if (!passwordEncoder.matches(password, user.getPassword())) {
//             return "Invalid password!";
//         }

//         // Validate role
//         if (user.getRole() != role) {
//             return "Role mismatch! Expected role: " + user.getRole();
//         }

//         return "Login successful! Welcome " + user.getName() + " (Role: " + role + ")";
//     }
// }



// package com.swms.swms.service;

// import com.swms.swms.model.User;
// import com.swms.swms.model.User.Role;
// import com.swms.swms.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import java.util.Optional;

// @Service
// public class AuthService {

//     @Autowired
//     private UserRepository userRepository;

//     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public String loginUser(String email, String password,Role role) {
//         Optional<User> userOptional = userRepository.findByEmail(email);

//         if (userOptional.isEmpty()) {
//             return "User not found!";
//         }

//         User user = userOptional.get();

//         // Validate password
//         if (!passwordEncoder.matches(password, user.getPassword())) {
//             return "Invalid password!";
//         }

//         // Validate role
//         if (user.getRole() != role) { // Direct enum comparison
//             return "Incorrect role!";
//         }
        

//         return "Login successful! Welcome " + user.getName()+ " (" + role + ")";
//     }
// }
