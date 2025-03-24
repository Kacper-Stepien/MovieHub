package com.example.movies_api.proxy;

import com.example.movies_api.dto.UpdatePasswordDto;
import com.example.movies_api.dto.UpdateUserDetailsDto;
import com.example.movies_api.dto.UserDto;

/**
 * Interface defining user service operations.
 * Used by both the real service and its proxy.
 */
public interface UserServiceInterface {
    void updateUserDetails(String email, UpdateUserDetailsDto dto);
    void updateUserPassword(String email, UpdatePasswordDto dto);
    UserDto getUserById(long userId);
}
