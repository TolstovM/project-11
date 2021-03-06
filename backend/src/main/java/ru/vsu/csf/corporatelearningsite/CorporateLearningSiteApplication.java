package ru.vsu.csf.corporatelearningsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.vsu.csf.corporatelearningsite.config.AppProperties;
import ru.vsu.csf.corporatelearningsite.config.HomeworkStorageProperties;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        MaterialStorageProperties.class, AppProperties.class, HomeworkStorageProperties.class
})
public class CorporateLearningSiteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CorporateLearningSiteApplication.class, args);
    }

}
