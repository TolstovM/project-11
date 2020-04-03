package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.AppRole;
import ru.vsu.csf.corporatelearningsite.model.Role;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.LoginRequest;
import ru.vsu.csf.corporatelearningsite.payload.SignUpRequest;
import ru.vsu.csf.corporatelearningsite.repository.UserRepository;
import ru.vsu.csf.corporatelearningsite.security.TokenProvider;

import java.util.*;

@Service
public class AuthService {

    public static final String EMAIL_ADDRESS_ALREADY_IN_USE_EXCEPTION_MESSAGE = "Email address already in use.";
    public static final String CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE = "Invite code does not exists";

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public User register(SignUpRequest signUpRequest) {
        checkUser(signUpRequest);
        User user = new User(
                UUID.fromString(signUpRequest.getInviteCode()),
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                Collections.singletonList(new Role(AppRole.ROLE_USER))
        );
        return userRepository.save(user);
    }

    private void checkUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException(EMAIL_ADDRESS_ALREADY_IN_USE_EXCEPTION_MESSAGE);
        }
        Optional<User> userOptional = userRepository.findById(UUID.fromString(signUpRequest.getInviteCode()));
        if (userOptional.isEmpty() || !userOptional.get().getEmail().isEmpty()) {
            throw new BadRequestException(CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }
    }

}
