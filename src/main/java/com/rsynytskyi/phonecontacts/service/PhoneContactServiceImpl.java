package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.dao.ContactRepo;
import com.rsynytskyi.phonecontacts.dao.EmailRepo;
import com.rsynytskyi.phonecontacts.dao.PhoneRepo;
import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Email;
import com.rsynytskyi.phonecontacts.model.Phone;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneContactServiceImpl implements PhoneContactService {

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    PhoneRepo phoneRepo;

    @Autowired
    EmailRepo emailRepo;

    @Override
    public Contact add(Contact contact, Usr usr) {
        contact.setUsr(usr);
        Contact cntct = new Contact();
        Contact dbContact = contactRepo.findByNameAndUsr(contact.getName(), usr);
        if (dbContact == null) {
            String totalInfo;

            Set<Email> email = contact.getEmails();
            Set<Email> savedEmails = new HashSet<>();
            email.stream().forEach(e -> savedEmails.add(emailRepo.findByEmail(e.getEmail())));

            Set<String> savedEmailsStrings = savedEmails.stream()
                    .map(e -> e.getEmail()).collect(Collectors.toSet());

            Set<Long> userIdsByEmail = new HashSet<>();
            for (Email e : savedEmails) {
                Long id = e.getId();
                userIdsByEmail = emailRepo.fetchUsers(id);
//                userIdsByEmail.add(userId);
            }
//            savedEmails.stream().forEach(e -> userIdsByEmail.add(emailRepo.fetchUsers(e.getId())));

            Set<Phone> phone = contact.getPhones();
            Set<String> savedPhones = phone.stream()
                    .filter(p -> phoneRepo.findAllByPhoneNbr(p.getPhoneNbr()).contains(p))
                    .map(p -> p.getPhoneNbr())
                    .collect(Collectors.toSet());

            if (!savedEmails.isEmpty() || !savedPhones.isEmpty()) {
                String existingEmailsInfo = composeString("Emails", savedEmailsStrings);
                String existingPhonesInfo = composeString("Phone numbers", savedPhones);
                if (!existingEmailsInfo.isEmpty()) {
                    totalInfo = existingEmailsInfo.concat(". ").concat(existingPhonesInfo);
                } else {
                    totalInfo = existingPhonesInfo;
                }
                cntct.setName(contact.getName());
                cntct.setInfo(totalInfo);
                return cntct;
            }

            email.stream().forEach(e -> e.setContact(contact));
            phone.stream().forEach(e -> e.setContact(contact));
            contactRepo.save(contact);
            cntct.setName(contact.getName());
            cntct.setInfo("Contact added");
            return cntct;
        } else {
            cntct.setName(contact.getName());
            cntct.setInfo("Contact with the same name already exists");
            return cntct;
        }
    }

    public Contact edit(Contact contact, Usr usr) {
        Contact cntct = contactRepo.findByNameAndUsr(contact.getName(), usr);
        cntct.setName(contact.getName());
        cntct.setEmails(contact.getEmails());
        cntct.setPhones(contact.getPhones());
        contactRepo.save(cntct);
        return contact;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contact> findAll(Usr usr) {
        return contactRepo.findAll()
                .stream().filter(c -> c.getUsr().getId().equals(usr.getId()))
                .peek(c -> c.getUsr().setPassword("Not displayable"))
                .collect(Collectors.toList());
    }

    private String composeString(String entity, Set<String> collection) {
        String reduce = collection.stream().reduce("", (acc, appender) -> {
            if (!"".equals(acc)) {
                return (acc + ", " + appender);
            } else {
                return appender;
            }
        });
        return entity + ": " + reduce + " already used by another user";
    }
}
