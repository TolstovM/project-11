package ru.vsu.csf.corporatelearningsite.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SaveCommentRequest {

    @NotBlank
    private Long lessonId;

    @NotBlank
    private UUID homeworkOwnerId;

    @NotBlank
    private String text;
}
