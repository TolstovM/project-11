package ru.vsu.csf.corporatelearningsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.vsu.csf.corporatelearningsite.config.AppProperties;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        MaterialStorageProperties.class, AppProperties.class
})
public class CorporateLearningSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorporateLearningSiteApplication.class, args);
    }

}
