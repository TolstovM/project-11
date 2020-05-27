package ru.vsu.csf.corporatelearningsite.services;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.payload.CheckHomeworkRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.HomeworkRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkServiceTest {

    @Autowired
    private HomeworkService homeworkService;

    @MockBean
    private HomeworkRepository homeworkRepository;

    @Test(expected = BadRequestException.class)
    public void checkHomeworkFailUserId() {
        //Проверка выброса исключения при неподходящем id пользователя
        UUID badId = null;
        CheckHomeworkRequest badCheckHomeworkRequest = new CheckHomeworkRequest();
        homeworkService.checkHomework(badCheckHomeworkRequest, badId);
    }

    @Test
    public void checkHomeworkSuccess() {
       UUID id =  UUID.fromString("29e98d16-5619-44d1-8282-2d9c0cf27bd2");
       CheckHomeworkRequest checkHomeworkRequest = new CheckHomeworkRequest(true, UUID.fromString("126e2dc5-b95b-4ad4-a6b6-bac96d512c0c"), (long)1);
       homeworkService.checkHomework(checkHomeworkRequest, id);
       Mockito.verify(homeworkRepository, Mockito.times(1)).checkHomework(checkHomeworkRequest.getUserId().toString(), checkHomeworkRequest.getLessonId(),
           checkHomeworkRequest.getResult());

    }
}
