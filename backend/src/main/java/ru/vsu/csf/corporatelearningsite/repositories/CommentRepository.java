package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Comment;
import ru.vsu.csf.corporatelearningsite.model.Homework;

import java.util.Optional;

@Repository()
@RestResource(exported = false)
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> deleteByHomework(Homework homework);
}
