package com.example.movies_api.proxy;

import com.example.movies_api.dto.UpdatePasswordDto;
import com.example.movies_api.dto.UpdateUserDetailsDto;
import com.example.movies_api.dto.UserDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Proxy for the UserService that adds logging, validation, and access control.
 */
@Component
public class UserServiceProxy implements UserServiceInterface {
    
    private final UserService userService;
    
    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void updateUserDetails(String email, UpdateUserDetailsDto dto) {
        System.out.println("UserServiceProxy: Updating user details for user: " + email);
        
        // Additional validation
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty");
        }
        if (dto.getFirstName() == null || dto.getFirstName().isEmpty()) {
            throw new BadRequestException("First name cannot be empty");
        }
        if (dto.getLastName() == null || dto.getLastName().isEmpty()) {
            throw new BadRequestException("Last name cannot be empty");
        }
        
        // Forward to real service
        userService.updateUserDetails(email, dto);
        System.out.println("UserServiceProxy: Successfully updated user details for: " + email);
    }
    
    @Override
    public void updateUserPassword(String email, UpdatePasswordDto dto) {
        System.out.println("UserServiceProxy: Updating password for user: " + email);
        
        // Additional validation
        if (dto.getOldPassword() == null || dto.getOldPassword().isEmpty()) {
            throw new BadRequestException("Old password cannot be empty");
        }
        if (dto.getNewPassword() == null || dto.getNewPassword().isEmpty()) {
            throw new BadRequestException("New password cannot be empty");
        }
        if (dto.getNewPassword().length() < 8) {
            throw new BadRequestException("New password must be at least 8 characters long");
        }
        
        // Forward to real service
        userService.updateUserPassword(email, dto);
        System.out.println("UserServiceProxy: Successfully updated password for: " + email);
    }
    
    @Override
    public UserDto getUserById(long userId) {
        System.out.println("UserServiceProxy: Getting user details for user ID: " + userId);
        
        // Forward to real service
        UserDto userDto = userService.getUserById(userId);
        System.out.println("UserServiceProxy: Successfully retrieved user details for ID: " + userId);
        
        return userDto;
    }
}
