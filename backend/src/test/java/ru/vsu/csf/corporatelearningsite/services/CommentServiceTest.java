package ru.vsu.csf.corporatelearningsite.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.*;
import ru.vsu.csf.corporatelearningsite.payload.SaveCommentRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CommentRepository;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class CommentServiceTest {

    private final static UUID OWNER_UUID = UUID.fromString("cb71df0c-5df9-4252-9840-e35330158645");
    private final static UUID INSTRUCTOR_UUID = UUID.fromString("27f8bb93-1a40-4f71-97b5-34c6c32fd200");
    private final static UUID STRANGER_UUID = UUID.fromString("70a1fc72-ff2d-45c0-a607-f880f2902f46");
    private final static Long LESSON_ID = 4l;

    private final List<Course> courses = new ArrayList<>();

    @Test()
    void saveComment() {
        CommentRepository commentRepository = mock(CommentRepository.class);
        CourseRepository courseRepository = mock(CourseRepository.class);
        when(courseRepository.isInstructorOnCourse(LESSON_ID, OWNER_UUID)).thenReturn(false);
        when(courseRepository.isInstructorOnCourse(LESSON_ID, INSTRUCTOR_UUID)).thenReturn(true);
        when(courseRepository.isInstructorOnCourse(LESSON_ID, STRANGER_UUID)).thenReturn(false);

        CommentService commentService = new CommentService(commentRepository, courseRepository);
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setLessonId(LESSON_ID);

        assertDoesNotThrow(() -> commentService.saveComment(OWNER_UUID, saveCommentRequest));

        assertDoesNotThrow(() -> commentService.saveComment(OWNER_UUID, saveCommentRequest));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> commentService.saveComment(STRANGER_UUID, saveCommentRequest));
        assertEquals(exception.getMessage(), CommentService.SAVE_COMMENT_ACCESS_EXCEPTION_MASSAGE);
    }
}