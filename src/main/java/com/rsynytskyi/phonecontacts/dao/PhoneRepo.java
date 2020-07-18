package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PhoneRepo extends JpaRepository<Phone, Integer> {

    public Set<Phone> findAllByPhoneNbr(String phoneNbr);

    @Query(value = "SELECT user_id FROM contacts c INNER JOIN phones p ON c.contact_id = p.contact_id WHERE p.id = :phone_id", nativeQuery = true)
    Set<Long> fetchUsers(@Param("phone_id") Long id);
}
