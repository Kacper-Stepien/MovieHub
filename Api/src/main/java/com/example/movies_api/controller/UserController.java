package com.example.movies_api.controller;

import com.example.movies_api.dto.UpdatePasswordDto;
import com.example.movies_api.dto.UpdateUserDetailsDto;
import com.example.movies_api.dto.UserDto;
import com.example.movies_api.proxy.UserServiceProxy;
import com.example.movies_api.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceProxy userServiceProxy;
    private final UserRepository userRepository;

    @PutMapping("/update-details")
    public ResponseEntity<Void> updateUserDetails(@Valid @RequestBody UpdateUserDetailsDto dto) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userServiceProxy.updateUserDetails(currentUserEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@Valid @RequestBody UpdatePasswordDto dto) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userServiceProxy.updateUserPassword(currentUserEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        var userDto = userServiceProxy.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
}