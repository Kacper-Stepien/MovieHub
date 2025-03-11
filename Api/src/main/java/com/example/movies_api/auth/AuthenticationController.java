package com.example.movies_api.auth;

import com.example.movies_api.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Wykorzystanie Singleton /////////////////////////////////////////////////////////////////////////////////////////////
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            BindingResult result
    ) {
        ResponseEntity<?> errors = ValidationService.getInstance().validate(result);
        if (errors != null) {
            return errors;
        }

        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult result
    ) {
        ResponseEntity<?> errors = ValidationService.getInstance().validate(result);
        if (errors != null) {
            return errors;
        }

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}


//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//    private final AuthenticationService authenticationService;
//
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
//        return ResponseEntity.ok(authenticationService.register(registerRequest));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }
//}

