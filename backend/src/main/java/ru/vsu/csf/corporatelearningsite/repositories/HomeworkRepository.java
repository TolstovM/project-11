package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;

@RepositoryRestResource
public interface HomeworkRepository extends JpaRepository<Homework, HomeworkId> {
}
