package ru.vsu.csf.corporatelearningsite.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "material")
public class MaterialStorageProperties {
    private String uploadDir;
}
