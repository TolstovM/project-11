package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Comment;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
