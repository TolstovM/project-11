package ru.vsu.csf.corporatelearningsite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CorporateLearningSiteApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private final String BASE_URL = "http://localhost:8080/api/";

    @Test
    public void checkHomeworkTestUnathenticated() throws Exception {
        String url = BASE_URL+"homework/checkHomework";
        //пользователь не авторизовался
        this.mockMvc.perform(post(url))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void checkHomeworkTest() throws Exception {
        String url = BASE_URL+"auth/login";
        MvcResult mvcResult = this.mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
            .content("{ \"email\": \"rakdotki322@gmail.com\", \"password\": \"123456\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        //Получаем токен
        String token = mvcResult.getResponse().getContentAsString().substring(16,mvcResult.getResponse().getContentAsString().length()-2);
        System.out.println(token);
        url = BASE_URL+"homework/checkHomework";
        this.mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
            .header("Authorization",token)
            .content("{ \"result\": \"true\", \"lessonId\": \"1\", \"userId\": \"126e2dc5-b95b-4ad4-a6b6-bac96d512c0c\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void correctLoginTest() throws Exception {

        String url = BASE_URL+"auth/login";
        this.mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
            .content("{ \"email\": \"rakdotki322@gmail.com\", \"password\": \"123456\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void incorrectLoginTest() throws Exception {

        String url = BASE_URL+"auth/login";
        this.mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
            .content("{ \"email\": \"rakdotki322@gmail.com\", \"password\": \"1\"}")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
}
