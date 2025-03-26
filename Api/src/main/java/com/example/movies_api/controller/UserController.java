package com.example.movies_api.controller;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.command.user.LogUserDetailsUpdateCommand;
import com.example.movies_api.command.user.LogUserPasswordChangeCommand;
import com.example.movies_api.dto.UpdatePasswordDto;
import com.example.movies_api.dto.UpdateUserDetailsDto;
import com.example.movies_api.dto.UserDto;
import com.example.movies_api.logger.FileLogWriter;
import com.example.movies_api.proxy.UserServiceProxy;
import com.example.movies_api.repository.UserRepository;
import com.example.movies_api.service.UserService;
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
    private final UserService userService;

    @PutMapping("/update-details")
    public ResponseEntity<Void> updateUserDetails(@Valid @RequestBody UpdateUserDetailsDto dto) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userServiceProxy.updateUserDetails(currentUserEmail, dto);

        LogCommand logCommand = new LogUserDetailsUpdateCommand(currentUserEmail);
        FileLogWriter writer = new FileLogWriter();
        logCommand.execute(writer);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@Valid @RequestBody UpdatePasswordDto dto) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        userServiceProxy.updateUserPassword(currentUserEmail, dto);

        LogCommand logCommand = new LogUserPasswordChangeCommand(currentUserEmail);
        FileLogWriter writer = new FileLogWriter();
        logCommand.execute(writer);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        var userDto = userServiceProxy.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/undo-last-update")
    public ResponseEntity<UserDto> undoLastUpdate(@RequestParam String email) {
        return ResponseEntity.ok(userService.undoLastUpdate(email));
    }
}