package ru.vsu.csf.corporatelearningsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
