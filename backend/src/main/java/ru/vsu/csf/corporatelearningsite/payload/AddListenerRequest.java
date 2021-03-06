package ru.vsu.csf.corporatelearningsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddListenerRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String id;
}
