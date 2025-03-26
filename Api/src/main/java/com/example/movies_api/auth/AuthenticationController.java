package com.example.movies_api.auth;

import com.example.movies_api.auth.adapter.JsonAuthenticationAdapter;
import com.example.movies_api.auth.adapter.XmlAuthenticationAdapter;
import com.example.movies_api.command.LogCommand;
import com.example.movies_api.command.user.LogUserLoginCommand;
import com.example.movies_api.command.user.LogUserRegisterCommand;
import com.example.movies_api.logger.FileLogWriter;
import com.example.movies_api.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//Added adapter for authenticating users via XML-based requests
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JsonAuthenticationAdapter jsonAdapter;
    private final XmlAuthenticationAdapter xmlAdapter;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            BindingResult result
    ) {
        ResponseEntity<?> errors = ValidationService.getInstance().validate(result);
        if (errors != null) {
            return errors;
        }

        LogCommand logCommand = new LogUserRegisterCommand(registerRequest);
        FileLogWriter writer = new FileLogWriter();
        logCommand.execute(writer);

        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateJson(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult result
    ) {
        ResponseEntity<?> errors = ValidationService.getInstance().validate(result);
        if (errors != null) {
            return errors;
        }

        LogCommand logCommand = new LogUserLoginCommand(request);
        FileLogWriter writer = new FileLogWriter();
        logCommand.execute(writer);

        return ResponseEntity.ok(jsonAdapter.authenticate(request));
    }

    //XML-based authentication (adapter)
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> authenticateXml(
            @RequestBody String xmlRequest
    ) {

        LogCommand logCommand = new LogUserLoginCommand(xmlRequest);
        FileLogWriter writer = new FileLogWriter();
        logCommand.execute(writer);

        return ResponseEntity.ok(xmlAdapter.authenticateXml(xmlRequest));
    }
}


/*
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
*/

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

