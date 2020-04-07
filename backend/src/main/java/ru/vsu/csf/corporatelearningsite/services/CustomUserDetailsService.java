package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.AppRole;
import ru.vsu.csf.corporatelearningsite.model.Role;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.UpdateAuthoritiesRequest;
import ru.vsu.csf.corporatelearningsite.repositories.RoleRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String USER_NOT_FOUND_WITH_EMAIL_EXCEPTION_MESSAGE = "User not found with email: %s";
    public static final String RESOURCE_NAME = "User";
    public static final String FIELD_NAME_ID = "id";
    public static final String USER_NOT_FOUND_WITH_ID_EXCEPTION_MESSAGE = "User nod found with id: %s";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public void updateAuthorities(UpdateAuthoritiesRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, request.getUserId()));

        updateAuthority(request.getIsAdmin(), AppRole.ROLE_ADMIN, user);
        updateAuthority(request.getIsInstructor(), AppRole.ROLE_INSTRUCTOR, user);
        userRepository.save(user);
    }

    private void updateAuthority(Boolean isInRole, AppRole appRole, User user) {
        if (isInRole == null) {
            return;
        }

        Set<Role> roles = roleRepository.getByName(appRole);
        if (isInRole) {
            roles.forEach(user::addRole);
        } else {
            roles.forEach(user::removeRole);
        }
    }
}
