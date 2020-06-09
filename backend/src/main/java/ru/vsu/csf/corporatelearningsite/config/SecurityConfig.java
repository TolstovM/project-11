package ru.vsu.csf.corporatelearningsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.vsu.csf.corporatelearningsite.model.AppRole;
import ru.vsu.csf.corporatelearningsite.security.RestAuthenticationEntryPoint;
import ru.vsu.csf.corporatelearningsite.security.TokenAuthenticationFilter;
import ru.vsu.csf.corporatelearningsite.security.TokenProvider;
import ru.vsu.csf.corporatelearningsite.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()

                .antMatchers("/api/user/me", "/api/user/me/change")
                .hasRole("USER")
                .antMatchers("/api/user/**")
                .hasAnyRole("ADMIN", "INSTRUCTOR")
                .antMatchers("/api/user/update/authorities", "/api/user/invite", "/api/user/search/findInstructorsByCourseId")
                .hasRole("ADMIN")

                .antMatchers("/api/course/*/mark")
                .authenticated()
                .antMatchers("/api/course/create", "/api/course/*/mark/**")
                .hasRole("INSTRUCTOR")
                .antMatchers("/api/course/addListener")
                .hasAnyRole("ADMIN", "INSTRUCTOR")
                .antMatchers("/api/course/*/instructors", "/api/course/*/instructors/*")
                .hasRole("ADMIN")

                .antMatchers("/api/lesson/courseName/**")
                .hasRole("INSTRUCTOR")
          
                .antMatchers("/api/lesson/delete/*")
                .hasAnyRole("ADMIN", "INSTRUCTOR")

                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
               
                .anyRequest()
                .authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
