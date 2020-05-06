package ru.vsu.csf.corporatelearningsite.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;
import ru.vsu.csf.corporatelearningsite.payload.UploadFileResponse;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.CheckHomeworkRequest;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.HomeworkService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(HomeworkController.API_HOMEWORK)
public class HomeworkController {

    public static final String API_HOMEWORK = "/api/homework";
    private HomeworkService homeworkService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping("/find/{lessonId}")
    public ResponseEntity<?> findOrCreate(@PathVariable("lessonId") Long lessonId,
                                          @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Homework> homework = this.homeworkService.findOrCreate(lessonId, userPrincipal.getId());
        return ResponseEntity.ok(homework);
    }

    @GetMapping("/findAll/{lessonId}")
    public ResponseEntity<?> findAllHomeworks(@PathVariable("lessonId") Long lessonId,
                                              @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Homework> result = this.homeworkService.getHomeworksByLessonId(lessonId, userPrincipal.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/findHomework")
    public ResponseEntity<?> findHomework(@RequestParam("lessonId") Long lessonId, @RequestParam("userId") UUID userId,
                                          @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Homework result = this.homeworkService.getHomeworkById(new HomeworkId(userId, lessonId), userPrincipal.getId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/uploadHomework/{lessonId}")
    public UploadFileResponse uploadHomework(@PathVariable("lessonId") Long lessonId,
                                             @RequestParam("homework") MultipartFile homework,
                                             @AuthenticationPrincipal Authentication authentication) {
        String homeworkName = homeworkService.storeHomework(homework, lessonId, (UserPrincipal) authentication.getPrincipal());
        String homeworkDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadHomework/")
                .path(homeworkName)
                .toUriString();
        return new UploadFileResponse(homeworkName, homeworkDownloadUri,
                homework.getContentType(), homework.getSize());
    }

    @GetMapping("/downloadHomework/{homeworkName:.+}")
    public ResponseEntity<Resource> downloadHomework(@PathVariable String homeworkName, HttpServletRequest request) {
        Resource resource = homeworkService.loadHomeworkAsResource(homeworkName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; homeworkname=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
  
    @PostMapping("/checkHomework")
    public ResponseEntity<?> checkHomework(@RequestBody CheckHomeworkRequest checkHomeworkRequest,
                                           @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        this.homeworkService.checkHomework(checkHomeworkRequest, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse(true, "Homework successfully changed"));
    }
}
