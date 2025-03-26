package com.example.movies_api.service;

import com.example.movies_api.dto.UpdatePasswordDto;
import com.example.movies_api.dto.UpdateUserDetailsDto;
import com.example.movies_api.dto.UserDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.memento.user_memento.UserCaretaker;
import com.example.movies_api.memento.user_memento.UserMemento;
import com.example.movies_api.proxy.UserServiceInterface;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.movies_api.constants.Messages.EMAIL_NOT_AVAILABLE;
import static com.example.movies_api.constants.Messages.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCaretaker userCaretaker;

    @Override
    public void updateUserDetails(String email, UpdateUserDetailsDto dto) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        userRepository.findByEmail(dto.getEmail())
                .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                .ifPresent(existingUser -> {
                    throw new BadRequestException(EMAIL_NOT_AVAILABLE);
                });


        //[memento] Save state before update
        userCaretaker.saveMemento(email, user.saveToMemento());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateUserPassword(String email, UpdatePasswordDto dto) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Stare hasło jest niepoprawne.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Nowe hasła nie są identyczne.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        return new UserDto(user.getFirstName(), user.getLastName());
    }

    // --- NEW [memento] RESTORE METHOD ---
    public UserDto undoLastUpdate(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        UserMemento memento = userCaretaker.getLastMemento(email);

        if (memento != null) {
            user.restoreFromMemento(memento);
            userRepository.save(user);
            return new UserDto(user.getFirstName(), user.getLastName());
        } else {
            throw new IllegalStateException("Brak zapisanej poprzedniej wersji danych.");
        }
    }
}


/*  //before memento pattern
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserDetails(String email, UpdateUserDetailsDto dto) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        userRepository.findByEmail(dto.getEmail())
                .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                .ifPresent(existingUser -> {
                    throw new BadRequestException(EMAIL_NOT_AVAILABLE);
                });

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateUserPassword(String email, UpdatePasswordDto dto) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Stare hasło jest niepoprawne.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Nowe hasła nie są identyczne.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto getUserById(long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        return new UserDto(user.getFirstName(), user.getLastName());
    }
}
*/