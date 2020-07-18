package com.rsynytskyi.phonecontacts.controller;

import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Usr;
import com.rsynytskyi.phonecontacts.service.PhoneContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "contacts")
public class ContactController {

    @Autowired
    private PhoneContactService phoneContactService;

    @PutMapping
    public Contact saveContact (@RequestBody Contact contact, @AuthenticationPrincipal Usr usr) {
        return phoneContactService.add(contact, usr);
    }

//    @PostMapping
//    public Contact editContact(@RequestBody Contact contact, @AuthenticationPrincipal User user) {
//        contact.setUser(user);
//        return phoneContactService.edit(contact, user);
//    }

    @GetMapping
    List<Contact> getAllContacts(@AuthenticationPrincipal Usr usr) {
        return phoneContactService.findAll(usr);
    }
}
