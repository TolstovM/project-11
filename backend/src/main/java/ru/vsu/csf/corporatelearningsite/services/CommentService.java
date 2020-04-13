package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.Comment;
import ru.vsu.csf.corporatelearningsite.payload.SaveCommentRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CommentRepository;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;

import java.util.UUID;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private CourseRepository courseRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CourseRepository courseRepository) {
        this.commentRepository = commentRepository;
        this.courseRepository = courseRepository;
    }

    public void saveComment(UUID userId, SaveCommentRequest saveCommentRequest) {
        if (userId == null || !userId.equals(saveCommentRequest.getHomeworkOwnerId())
                || !this.courseRepository.isInstructorOnCourse(saveCommentRequest.getLessonId(), userId)) {
            throw new BadRequestException("You cannot send that comment");
        }
        Comment comment = new Comment(userId, saveCommentRequest.getLessonId(), saveCommentRequest.getHomeworkOwnerId(), saveCommentRequest.getText());
        this.commentRepository.save(comment);
    }
}
