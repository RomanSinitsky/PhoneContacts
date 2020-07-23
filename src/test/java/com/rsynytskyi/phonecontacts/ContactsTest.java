package com.rsynytskyi.phonecontacts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsynytskyi.phonecontacts.dao.ContactRepo;
import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Email;
import com.rsynytskyi.phonecontacts.model.Phone;
import com.rsynytskyi.phonecontacts.model.Usr;
import com.rsynytskyi.phonecontacts.service.PhoneContactService;
import com.rsynytskyi.phonecontacts.service.UserService;
import org.junit.After;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("R")
public class ContactsTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PhoneContactService contactService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    UserService userService;

    @Test
    public void saveContact() throws Exception {
        Email email = new Email();
        email.setEmail("testmail@testmail.ua");
        Phone phone = new Phone();
        phone.setPhoneNbr("123123123");
        Contact contact = new Contact();
        contact.setName("testContact");
        contact.setEmails(Collections.singleton(email));
        contact.setPhones(Collections.singleton(phone));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("R", "abc123");
        HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/contacts", HttpMethod.PUT, entity, String.class);
        String body = exchange.getBody();
        Contact contactFromJson = objectMapper.readValue(body, Contact.class);
        String name = contactFromJson.getName();
        Usr usr = getUserFromAuthentication();
        String info = contactFromJson.getInfo();

        Contact databaseContact = contactRepo.findByNameAndUsr(name, usr);

        assertEquals("Contact added", info);
        assertNotNull(databaseContact);
    }

    private Usr getUserFromAuthentication() {
        String userName = null;
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details instanceof UserDetails) {
            userName = ((UserDetails) details).getUsername();
        } else {
            userName = details.toString();
        }
        Usr user = userService.findByName(userName);
        return user;
    }

    @After
    public void cleanAddedUser() {
        Contact testContact = contactService.delete("testContact", getUserFromAuthentication());
    }


}
