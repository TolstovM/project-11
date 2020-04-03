package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.repository.UserRepository;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String USER_NOT_FOUND_WITH_EMAIL_EXCEPTION_MESSAGE = "User not found with email: %s";
    public static final String RESOURCE_NAME = "User";
    public static final String FIELD_NAME_ID = "id";
    public static final String USER_NOT_FOUND_WITH_ID_EXCEPTION_MESSAGE = "User nod found with id: %s";

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_WITH_EMAIL_EXCEPTION_MESSAGE, email)));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, id));
        return UserPrincipal.create(user);
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
}
