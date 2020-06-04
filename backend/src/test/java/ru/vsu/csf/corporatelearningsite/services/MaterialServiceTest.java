package ru.vsu.csf.corporatelearningsite.services;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.exceptions.MaterialStorageException;
import ru.vsu.csf.corporatelearningsite.model.*;
import ru.vsu.csf.corporatelearningsite.repositories.LessonRepository;
import ru.vsu.csf.corporatelearningsite.repositories.MaterialRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class MaterialServiceTest {

    private final static String FILE_NAME = "file.txt";
    private final static String FILE_CONTENT_TYPE = "text/plain";
    private final static String FILE_CONTENT = "file content";
    private final static String BAD_FILE_NAME = "file..txt";
    private final static Long LESSON_ID = 1L;

    @Test()
    void storeMaterial() {
        MaterialRepository materialRepository = mock(MaterialRepository.class);
        LessonRepository lessonRepository = mock(LessonRepository.class);
        MaterialStorageProperties materialStorageProperties = mock(MaterialStorageProperties.class);

        MaterialService materialService = new MaterialService(materialRepository, materialStorageProperties, lessonRepository);

        MultipartFile file = new MockMultipartFile(FILE_NAME,
                FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        assertDoesNotThrow(() -> materialService.storeMaterial(file, LESSON_ID));

        assertDoesNotThrow(() -> materialService.storeMaterial(file, LESSON_ID));

        MultipartFile fileWithBadPath = new MockMultipartFile(FILE_NAME,
                BAD_FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        MaterialStorageException exception = assertThrows(MaterialStorageException.class, () -> materialService.storeMaterial(fileWithBadPath, LESSON_ID));
        assertEquals(exception.getMessage(), MaterialService.BAD_PATH_EXCEPTION_MASSAGE);
    }
}
