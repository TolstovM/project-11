package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.UpdateUserRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
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
