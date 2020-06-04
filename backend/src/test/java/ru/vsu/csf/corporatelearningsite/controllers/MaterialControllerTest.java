package ru.vsu.csf.corporatelearningsite.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;
import ru.vsu.csf.corporatelearningsite.exceptions.MaterialStorageException;
import ru.vsu.csf.corporatelearningsite.payload.UploadFileResponse;
import ru.vsu.csf.corporatelearningsite.repositories.LessonRepository;
import ru.vsu.csf.corporatelearningsite.repositories.MaterialRepository;
import ru.vsu.csf.corporatelearningsite.services.MaterialService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class MaterialControllerTest {

    private final static String FILE_NAME = "file.txt";
    private final static String BAD_FILE_NAME = "file..txt";
    private final static String FILE_CONTENT_TYPE = "text/plain";
    private final static String FILE_CONTENT = "file content";
    private final static Long LESSON_ID = 1L;

    @Test
    void uploadMaterial() {
        MultipartFile file = new MockMultipartFile(FILE_NAME,
                FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        MultipartFile fileWithBadPath = new MockMultipartFile(FILE_NAME,
                BAD_FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        MaterialRepository materialRepository = mock(MaterialRepository.class);
        LessonRepository lessonRepository = mock(LessonRepository.class);
        MaterialStorageProperties materialStorageProperties = mock(MaterialStorageProperties.class);

        MaterialService materialService = new MaterialService(materialRepository, materialStorageProperties, lessonRepository);

        MaterialController materialController = new MaterialController(materialService);

        ResponseEntity<UploadFileResponse> uploadMaterial = materialController.uploadMaterial(LESSON_ID, file);
        assertTrue(uploadMaterial.getStatusCode().is2xxSuccessful());

        MaterialStorageException exception = assertThrows(MaterialStorageException.class, () -> materialService.storeMaterial(fileWithBadPath, LESSON_ID));
        assertEquals(exception.getMessage(), MaterialService.BAD_PATH_EXCEPTION_MASSAGE);
    }
}
