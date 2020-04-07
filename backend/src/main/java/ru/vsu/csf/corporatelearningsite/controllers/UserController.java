package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.UpdateAuthoritiesRequest;
import ru.vsu.csf.corporatelearningsite.security.CurrentUser;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CustomUserDetailsService;

import javax.validation.Valid;

@RestController
@RequestMapping(UserController.URL)
public class UserController {

    public static final String CURRENT_USER_PATH = "/me";
    public static final String URL = "/api/user";
    public static final String UPDATE_AUTHORITIES_PATH = "/update/authorities";
    public static final String UPDATE_AUTHORITIES_RESPONSE_MESSAGE = "User's authorities updated.";

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public UserController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(CURRENT_USER_PATH)
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userDetailsService.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        CustomUserDetailsService.RESOURCE_NAME,
                        CustomUserDetailsService.FIELD_NAME_ID,
                        userPrincipal.getId()
                ));
    }

    @PatchMapping(UPDATE_AUTHORITIES_PATH)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAuthorities(@Valid @RequestBody UpdateAuthoritiesRequest updateAuthoritiesRequest) {
        userDetailsService.updateAuthorities(updateAuthoritiesRequest);
        return ResponseEntity.ok(new ApiResponse(true, UPDATE_AUTHORITIES_RESPONSE_MESSAGE));
    }
}
