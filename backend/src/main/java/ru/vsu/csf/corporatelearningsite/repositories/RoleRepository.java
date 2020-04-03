package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.corporatelearningsite.model.AppRole;
import ru.vsu.csf.corporatelearningsite.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public List<Role> getByName(AppRole name);
}
