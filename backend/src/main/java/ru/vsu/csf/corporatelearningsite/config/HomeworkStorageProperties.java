package ru.vsu.csf.corporatelearningsite.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "homework")
public class HomeworkStorageProperties {
    private String uploadDir;
}
