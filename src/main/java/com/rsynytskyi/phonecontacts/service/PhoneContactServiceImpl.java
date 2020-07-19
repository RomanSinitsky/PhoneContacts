package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.dao.ContactRepo;
import com.rsynytskyi.phonecontacts.dao.EmailRepo;
import com.rsynytskyi.phonecontacts.dao.PhoneRepo;
import com.rsynytskyi.phonecontacts.model.Contact;
import com.rsynytskyi.phonecontacts.model.Email;
import com.rsynytskyi.phonecontacts.model.Phone;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Contact ct = new Contact();
        Contact dbContact = contactRepo.findByNameAndUsr(contact.getName(), usr);
        if (dbContact == null) {
            Set<Email> email = contact.getEmails();
            Set<Phone> phone = contact.getPhones();

            String totalInfo = checkExistingContactDetails(contact, usr);
            if (!totalInfo.isEmpty()) {
                ct.setName(contact.getName());
                ct.setInfo(totalInfo);
                return ct;
            }

            email.stream().forEach(e -> e.setContact(contact));
            phone.stream().forEach(e -> e.setContact(contact));
            contactRepo.save(contact);
            ct.setName(contact.getName());
            ct.setInfo("Contact added");
            return ct;
        } else {
            ct.setName(contact.getName());
            ct.setInfo("Contact with the same name already exists");
            return ct;
        }
    }

    public Contact edit(Contact contact, Usr usr) {
        contact.setUsr(usr);
        Contact ct = new Contact();
        Contact databaseContact = contactRepo.findByNameAndUsr(contact.getName(), usr);
        if (databaseContact == null) {
            ct.setName(contact.getName());
            ct.setInfo("Such contact does not exist");
            return ct;
        } else {
            String totalInfo = checkExistingContactDetails(contact, usr);
            if (totalInfo.isEmpty()) {
                databaseContact.setName(contact.getName());
                Set<Email> emails = databaseContact.getEmails();
                emails.clear();
                emails.addAll(contact.getEmails());
                Set<Phone> phones = databaseContact.getPhones();
                phones.clear();
                phones.addAll(contact.getPhones());
                contactRepo.save(databaseContact);
                databaseContact.setInfo("Contact altered");
                return databaseContact;
            } else {
                ct.setInfo(totalInfo);
                ct.setName(contact.getName());
                return ct;
            }
        }
    }

    @Override
    public Contact delete(String contactName, Usr usr) {
        Contact ct = new Contact();
        Contact databaseContact = contactRepo.findByNameAndUsr(contactName, usr);
        if (databaseContact != null) {
            contactRepo.delete(databaseContact);
            ct.setName(contactName);
            ct.setInfo("Contact is deleted");
            return ct;
        }
        else {
            ct.setInfo("Such contact does not exist");
            return ct;
        }
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

    private String checkExistingContactDetails(Contact contact, Usr usr) {
        List<Email> savedEmails = new ArrayList<>();
        List<Phone> savedPhones = new ArrayList<>();
        Set<String> savedEmailsStrings = new HashSet<>();
        Set<String> savedPhonesStrings = new HashSet<>();
        contact.setUsr(usr);

        Set<Email> email = contact.getEmails();
        email.stream().forEach(e -> savedEmails.addAll(emailRepo.findAllByEmail(e.getEmail())));

        for (Email e : savedEmails) {
            Long id = e.getId();
            Set<Long> userIdsByEmail = emailRepo.fetchUsers(id);
            if (userIdsByEmail.contains(usr.getId())) {
                savedEmailsStrings.add(e.getEmail());
            }
        }

        Set<Phone> phone = contact.getPhones();
        phone.stream().forEach(p -> savedPhones.addAll(phoneRepo.findAllByPhoneNbr(p.getPhoneNbr())));

        for (Phone p : savedPhones) {
            Long id = p.getId();
            Set<Long> userIdsByPhone = phoneRepo.fetchUsers(id);
            if (userIdsByPhone.contains(usr.getId())) {
                savedPhonesStrings.add(p.getPhoneNbr());
            }
        }

        if (!savedEmailsStrings.isEmpty() || !savedPhonesStrings.isEmpty()) {
            String existingEmailsInfo = composeString("Emails", savedEmailsStrings);
            String existingPhonesInfo = composeString("Phone numbers", savedPhonesStrings);
            String totalInfo;
            if (!existingEmailsInfo.isEmpty()) {
                if (!existingPhonesInfo.isEmpty()) {
                    totalInfo = existingEmailsInfo.concat(". ").concat(existingPhonesInfo);
                }
                else {totalInfo = existingEmailsInfo;
                }
            } else {
                totalInfo = existingPhonesInfo;
            }
            return totalInfo;
        } else return "";
    }
}
