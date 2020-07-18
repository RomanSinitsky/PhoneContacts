package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

    Contact findByNameAndUsr(String name, Usr usr);
}
