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
import ru.vsu.csf.corporatelearningsite.payload.UpdateUserRequest;
import ru.vsu.csf.corporatelearningsite.repositories.RoleRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;
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

    private RoleRepository roleRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       TokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public User register(SignUpRequest signUpRequest) {
        User user = checkUser(signUpRequest);
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Set<Role> roles = (Set<Role>) roleRepository.getByName(AppRole.ROLE_USER);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    private User checkUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException(EMAIL_ADDRESS_ALREADY_IN_USE_EXCEPTION_MESSAGE);
        }
        Optional<User> userOptional = userRepository.findById(UUID.fromString(signUpRequest.getInviteCode()));
        if (!userOptional.isPresent() || userOptional.get().getEmail() != null) {
            throw new BadRequestException(CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
        }
        return userOptional.get();
    }

    public Boolean updateUser(UpdateUserRequest request, UUID id){
        if(request.getName()!=null && request.getEmail()!=null){
            userRepository.updateUser(request.getName(),request.getEmail(),id);
            return true;
        }
        else if(request.getPassword()!=null){
            userRepository.changePassword(passwordEncoder.encode(request.getPassword()),id);
            return true;
        }
        return false;
    }

}
