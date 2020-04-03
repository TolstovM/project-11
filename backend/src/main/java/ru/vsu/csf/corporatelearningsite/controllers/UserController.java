package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.security.CurrentUser;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CustomUserDetailsService;

@RestController
public class UserController {

    public static final String CURRENT_USER_PATH = "/api/user/me";

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
}
