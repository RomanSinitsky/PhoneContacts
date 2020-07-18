package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface EmailRepo extends JpaRepository<Email, Integer> {

    public Set<Email> findAllByEmail(String email);

    @Query(value = "SELECT user_id FROM contacts c INNER JOIN emails e ON c.contact_id = e.contact_id WHERE e.id = :email_id", nativeQuery = true)
    public Set<Long> fetchUsers(@Param("email_id") Long id);
}
