package ru.vsu.csf.corporatelearningsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckHomeworkRequest {

    @NotNull
    private Boolean result;


    @NotNull
    private UUID userId;

    @NotNull
    private Long lessonId;


}
