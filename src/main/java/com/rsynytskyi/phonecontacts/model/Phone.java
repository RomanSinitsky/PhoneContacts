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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "PHONES")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "phone number must not be empty")
    @Pattern(regexp = "[^a-z, ^A-Z]*", message = "Must not contain letters")
    @Size(min = 7, max = 20, message = "Phone number length must be from 7 to 20 digits")
    private String phoneNbr;

    @ManyToOne(optional = false, targetEntity = Contact.class, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    public Phone(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public Phone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
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
        Phone phone = (Phone) o;
        return phoneNbr.equals(phone.phoneNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNbr);
    }
}
