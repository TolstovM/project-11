package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.AuthResponse;
import ru.vsu.csf.corporatelearningsite.payload.LoginRequest;
import ru.vsu.csf.corporatelearningsite.payload.SignUpRequest;
import ru.vsu.csf.corporatelearningsite.services.AuthService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(AuthController.URL)
public class AuthController {

    public static final String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully";
    public static final String LOGIN_PATH = "/login";
    public static final String SIGNUP_PATH = "/signup";
    public static final String URL = "/api/auth";

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(LOGIN_PATH)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @RequestMapping(SIGNUP_PATH)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = authService.register(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(UserController.URL + UserController.CURRENT_USER_PATH)
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, USER_REGISTERED_SUCCESSFULLY_MESSAGE));

    }
}
