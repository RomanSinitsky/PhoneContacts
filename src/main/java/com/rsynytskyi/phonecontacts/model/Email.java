package com.rsynytskyi.phonecontacts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "EMAILS")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "email must not be empty")
    @javax.validation.constraints.Email(message = "Email address must conform email-format")
    private String email;

    @ManyToOne(optional = false, targetEntity = Contact.class, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    public Email(String email) {
        this.email = email;
    }

    public Email() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
