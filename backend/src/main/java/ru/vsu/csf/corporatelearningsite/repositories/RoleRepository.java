package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.corporatelearningsite.model.AppRole;
import ru.vsu.csf.corporatelearningsite.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestResource(exported = false)
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> getByName(AppRole name);
}
