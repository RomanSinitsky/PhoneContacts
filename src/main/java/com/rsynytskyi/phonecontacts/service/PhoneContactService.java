package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Usr;

import java.util.List;

public interface PhoneContactService {

    Contact add(Contact contact, Usr usr);

    List<Contact> findAll(Usr usr);

    Contact edit(Contact contact, Usr usr);
}
