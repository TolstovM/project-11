package ru.vsu.csf.corporatelearningsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    private String email;

    private String name;

    private String password;
}
