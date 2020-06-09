package ru.vsu.csf.corporatelearningsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableSwagger2WebMvc
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    public static final String CLIENT_ID = "ab8577fe-7c2c-11ea-a7e7-d0509938df5a";
    public static final String CLIENT_SECRET = "qwerty";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String KEY_NAME = "JWT";
    public static final String PASS_AS = "header";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(
                        Authentication.class,
                        UserPrincipal.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/error").negate())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .forPaths(PathSelectors.ant("/api/auth/**").negate())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(KEY_NAME, AUTHORIZATION_HEADER, PASS_AS);
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] =  new AuthorizationScope("listener", "Слушатель курсов");
        authorizationScopes[1] = new AuthorizationScope("instructor", "Инструктор на курсах");
        authorizationScopes[2] = new AuthorizationScope("admin", "Администратор");
        return Arrays.asList(new SecurityReference(KEY_NAME, authorizationScopes));
    }
}
