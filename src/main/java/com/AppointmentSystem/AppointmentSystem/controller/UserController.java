package com.AppointmentSystem.AppointmentSystem.controller;

import com.AppointmentSystem.AppointmentSystem.dto.Requests.UserRequest;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.ApiResponse;
import com.AppointmentSystem.AppointmentSystem.dto.Responses.UserResponse;
import com.AppointmentSystem.AppointmentSystem.exception.NotFoundException;
import com.AppointmentSystem.AppointmentSystem.model.User;
import com.AppointmentSystem.AppointmentSystem.repository.UserRepository;
import com.AppointmentSystem.AppointmentSystem.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends com.AppointmentSystem.AppointmentSystem.controller.BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @jakarta.validation.Valid @RequestBody UserRequest userRequest) {
        // Map request DTO -> entity (keep mapping simple for now)
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        User createdUser = userService.createUser(user);
        return ok(UserResponse.from(createdUser), "User created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ok(UserResponse.from(user), "User retrieved");
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return ok(UserResponse.from(user), "User profile retrieved");
    }
}