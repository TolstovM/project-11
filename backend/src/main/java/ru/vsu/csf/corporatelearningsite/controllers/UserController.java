package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.UpdateAuthoritiesRequest;
import ru.vsu.csf.corporatelearningsite.security.CurrentUser;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.AuthService;
import ru.vsu.csf.corporatelearningsite.services.CustomUserDetailsService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(UserController.URL)
public class UserController {

    public static final String CURRENT_USER_PATH = "/me";
    public static final String CURRENT_USER_CHANGE_PROFILE_PATH = "/me/change";
    public static final String URL = "/api/user";
    public static final String GET_USERNAME_BY_ID = "/getById";
    public static final String UPDATE_AUTHORITIES_PATH = "/update/authorities";
    public static final String UPDATE_AUTHORITIES_RESPONSE_MESSAGE = "User's authorities updated.";
    public static final String INVITE_CODE_PATH = "invite";

    private CustomUserDetailsService userDetailsService;

    private AuthService userService;

    @Autowired
    public UserController(final CustomUserDetailsService userDetailsService, final AuthService userService) {
        this.userService = userService;
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

    @PostMapping(CURRENT_USER_CHANGE_PROFILE_PATH)
    public Map<String,String> update(@RequestBody UpdateUserRequest request) {
        Map<String, String> res = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userDetailsService.findByEmail(name);
        if (userService.updateUser(request, user.getId()))
            res.put("message", "Успешное изменение данных");
        else
            res.put("message", "Ошибка при попытке изменить данные");
        return res;
    }

    @PatchMapping(UPDATE_AUTHORITIES_PATH)
    public ResponseEntity<?> updateAuthorities(@Valid @RequestBody UpdateAuthoritiesRequest updateAuthoritiesRequest) {
        userDetailsService.updateAuthorities(updateAuthoritiesRequest);
        return ResponseEntity.ok(new ApiResponse(true, UPDATE_AUTHORITIES_RESPONSE_MESSAGE));
    }

    @GetMapping(INVITE_CODE_PATH)
    public ResponseEntity<?> generateInviteCode() {
        UUID code = this.userDetailsService.generateInviteCode();
        return ResponseEntity.ok(code);
    }


}
