package ru.vsu.csf.corporatelearningsite.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AddCourseRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}