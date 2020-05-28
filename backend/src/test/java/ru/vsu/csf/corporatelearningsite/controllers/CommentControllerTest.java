package ru.vsu.csf.corporatelearningsite.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.payload.SaveCommentRequest;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CommentService;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    private final static UUID OWNER_UUID = UUID.fromString("cb71df0c-5df9-4252-9840-e35330158645");
    private final static UUID INSTRUCTOR_UUID = UUID.fromString("27f8bb93-1a40-4f71-97b5-34c6c32fd200");
    private final static UUID STRANGER_UUID = UUID.fromString("70a1fc72-ff2d-45c0-a607-f880f2902f46");
    private final static Long LESSON_ID = 4l;

    @Test
    void send() {
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setLessonId(LESSON_ID);

        CommentService commentService = mock(CommentService.class);
        BadRequestException exception = new BadRequestException(CommentService.SAVE_COMMENT_ACCESS_EXCEPTION_MASSAGE);
        Mockito.doThrow(exception).when(commentService).saveComment(STRANGER_UUID, saveCommentRequest);

        CommentController commentController = new CommentController(commentService);

        Authentication authenticationOwner = getAuthenticationFor(OWNER_UUID);
        ResponseEntity<?> sendByOwner = commentController.send(saveCommentRequest, authenticationOwner);
        assertTrue(sendByOwner.getStatusCode().is2xxSuccessful());

        Authentication authenticationStranger = getAuthenticationFor(STRANGER_UUID);
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> commentController.send(saveCommentRequest, authenticationStranger));
        assertEquals(badRequestException.getMessage(), CommentService.SAVE_COMMENT_ACCESS_EXCEPTION_MASSAGE);
    }

    private Authentication getAuthenticationFor(UUID userId) {
        return new Authentication() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return new UserPrincipal(userId, null, null, null, null);
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
    }
}