package com.rsynytskyi.phonecontacts.controller;

import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Usr;
import com.rsynytskyi.phonecontacts.service.PhoneContactService;
import com.rsynytskyi.phonecontacts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private UserService userService;

    @PutMapping
    public Contact saveContact (@RequestBody Contact contact, @AuthenticationPrincipal Usr usr) {
        if (usr == null) {
            usr = getUserFromAuthentication();
        }
        return phoneContactService.add(contact, usr);
    }

    @PostMapping
    public Contact editContact(@RequestBody Contact contact, @AuthenticationPrincipal Usr usr) {
        if (usr == null) {
            usr = getUserFromAuthentication();
        }
        return phoneContactService.edit(contact, usr);
    }

    @DeleteMapping(path = "{contactName}")
    public Contact deleteContact(@PathVariable String contactName, @AuthenticationPrincipal Usr usr) {
        if (usr == null) {
            usr = getUserFromAuthentication();
        }
        return phoneContactService.delete(contactName, usr);
    }

    @GetMapping
    List<Contact> getAllContacts(@AuthenticationPrincipal Usr usr) {
        if (usr == null) {
            usr = getUserFromAuthentication();
        }
        return phoneContactService.findAll(usr);
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
}
