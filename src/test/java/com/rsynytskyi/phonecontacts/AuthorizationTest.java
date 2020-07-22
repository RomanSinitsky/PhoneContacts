package com.rsynytskyi.phonecontacts;

import com.rsynytskyi.phonecontacts.controller.ContactController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {

    @Autowired
    ContactController contactController;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void accessUnauthorized() throws Exception {
        this.mockMvc
                .perform(get(anyString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authSuccessful() throws Exception {

        this.mockMvc.perform(get("http://localhost:8080/auth")
                .with(httpBasic("R", "abc123")))
                .andExpect(status().isOk());
    }

    @Test
    public void authSuccessfulBearerTokenIncluded() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("http://localhost:8080/auth")
                .with(httpBasic("R", "abc123"))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("Bearer"));
    }

//    Using restTemplate
    @Test
    public void authSuccessfulBearerTokenIncluded2() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("R", "abc123");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/auth", HttpMethod.GET, entity, String.class);
        String content = exchange.getBody();
        assertTrue(content.contains("Bearer"));
    }

}
