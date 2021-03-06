package ru.vsu.csf.corporatelearningsite.controllers;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.SaveCommentRequest;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CommentService;

import javax.validation.Valid;

@RepositoryRestController
@RequestMapping(CommentController.URI)
public class CommentController {

    public static final String URI = "/api/comment";
    public static final String SEND_PATH = "/send";
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(SEND_PATH)
    public ResponseEntity<?> send(@Valid @RequestBody SaveCommentRequest saveCommentRequest,
                                  @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        this.commentService.saveComment(userPrincipal.getId(), saveCommentRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Comment successfully saved"));
    }
}
