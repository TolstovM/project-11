package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.UpdateUserRequest;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Role;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.model.projections.UserWithRolesProjection;

import javax.annotation.security.PermitAll;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = UserRepository.COLLECTION_REL, path = UserRepository.PATH, excerptProjection = UserWithRolesProjection.class)
public interface UserRepository extends JpaRepository<User, UUID> {

    public static final String COLLECTION_REL = "users";
    public static final String PATH = "user";

    @PermitAll
    Optional<User> findByEmail(String email);
    @PermitAll
    Boolean existsByEmail(String email);




    Optional<User> findByName(String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.name = ?1, u.email =?2 where u.id = ?3")
    void updateUser(String name, String email, UUID id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void changePassword(String password, UUID id);
}
