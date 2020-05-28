package ru.vsu.csf.corporatelearningsite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.LoginRequest;
import ru.vsu.csf.corporatelearningsite.payload.SaveCommentRequest;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;
import ru.vsu.csf.corporatelearningsite.services.CommentService;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(value = {"classpath:scripts/dml-test-comment.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentControllerIntegrationTest {

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ERROR_IN_DML_SCRIPT_MESSAGE = "Error in dml script";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();



    private UUID OWNER_UUID = UUID.fromString("cb71df0c-5df9-4252-9840-e35330158645");
    private final static String OWNER_EMAIL = "OWNER@OWNER";
    private final static String OWNER_PASSWORD = "qwerty";
    private final static UUID INSTRUCTOR_UUID = UUID.fromString("27f8bb93-1a40-4f71-97b5-34c6c32fd200");
    private final static String INSTRUCTOR_EMAIL = "INSTRUCTOR@INSTRUCTOR";
    private final static String INSTRUCTOR_PASSWORD = "123456qwerty";
    private final static UUID STRANGER_UUID = UUID.fromString("70a1fc72-ff2d-45c0-a607-f880f2902f46");
    private final static String STRANGER_EMAIL = "STRANGER@STRANGER";
    private final static String STRANGER_PASSWORD = "123456";
    private final static Long LESSON_ID = 1l;

    @BeforeEach
    void setUp() {
        User owner = userRepository.findById(OWNER_UUID).orElseThrow(() -> new RuntimeException(ERROR_IN_DML_SCRIPT_MESSAGE));
        owner.setPassword(passwordEncoder.encode(OWNER_PASSWORD));
        owner.setEmail(OWNER_EMAIL);
        userRepository.save(owner);

        User instructor = userRepository.findById(INSTRUCTOR_UUID).orElseThrow(() -> new RuntimeException(ERROR_IN_DML_SCRIPT_MESSAGE));
        instructor.setPassword(passwordEncoder.encode(INSTRUCTOR_PASSWORD));
        instructor.setEmail(INSTRUCTOR_EMAIL);
        userRepository.save(instructor);

        User stranger = userRepository.findById(STRANGER_UUID).orElseThrow(() -> new RuntimeException(ERROR_IN_DML_SCRIPT_MESSAGE));
        stranger.setPassword(passwordEncoder.encode(STRANGER_PASSWORD));
        stranger.setEmail(STRANGER_EMAIL);
        userRepository.save(stranger);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void sendFromOwner200() throws Exception {
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setLessonId(LESSON_ID);
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setText("text");

        String token = login(OWNER_EMAIL, OWNER_PASSWORD);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION_HEADER, token);

        HttpEntity<SaveCommentRequest> saveCommentRequestHttpEntity = new HttpEntity<>(saveCommentRequest, httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(CommentController.URI + CommentController.SEND_PATH, saveCommentRequestHttpEntity, String.class);

        Map<String, String> map = objectMapper.readValue(stringResponseEntity.getBody(), Map.class);

        assertTrue(stringResponseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void sendFromInstructor200() throws Exception {
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setLessonId(LESSON_ID);
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setText("text");

        String token = login(INSTRUCTOR_EMAIL, INSTRUCTOR_PASSWORD);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION_HEADER, token);

        HttpEntity<SaveCommentRequest> saveCommentRequestHttpEntity = new HttpEntity<>(saveCommentRequest, httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(CommentController.URI + CommentController.SEND_PATH, saveCommentRequestHttpEntity, String.class);

        Map<String, String> map = objectMapper.readValue(stringResponseEntity.getBody(), Map.class);

        assertTrue(stringResponseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void sendFromStranger400() throws Exception {
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setLessonId(LESSON_ID);
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setText("text");

        String token = login(STRANGER_EMAIL, STRANGER_PASSWORD);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION_HEADER, token);

        HttpEntity<SaveCommentRequest> saveCommentRequestHttpEntity = new HttpEntity<>(saveCommentRequest, httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(CommentController.URI + CommentController.SEND_PATH, saveCommentRequestHttpEntity, String.class);

        Map<String, String> map = objectMapper.readValue(stringResponseEntity.getBody(), Map.class);

        assertTrue(map.containsValue(CommentService.SAVE_COMMENT_ACCESS_EXCEPTION_MASSAGE));
        assertEquals(stringResponseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void sendNotAuthorized() {
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest();
        saveCommentRequest.setLessonId(LESSON_ID);
        saveCommentRequest.setHomeworkOwnerId(OWNER_UUID);
        saveCommentRequest.setText("text");

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(CommentController.URI + CommentController.SEND_PATH, saveCommentRequest, String.class);

        assertEquals(stringResponseEntity.getStatusCodeValue(), 401);
    }

    private String login(String email, String password) throws JsonProcessingException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(AuthController.URL + AuthController.LOGIN_PATH, loginRequest, String.class);
        Map<String, String> map = objectMapper.readValue(responseEntity.getBody(), Map.class);
        assertTrue(map.containsKey(ACCESS_TOKEN_KEY));
        return map.get(ACCESS_TOKEN_KEY);
    }
}