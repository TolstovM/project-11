package ru.vsu.csf.corporatelearningsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAuthoritiesRequest {

    private UUID userId;
    private Boolean isAdmin;
    private Boolean isInstructor;
}
