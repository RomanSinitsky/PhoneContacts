package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepo extends JpaRepository<Phone, Integer> {

    public List<Phone> findAllByPhoneNbr(String phoneNbr);
}
