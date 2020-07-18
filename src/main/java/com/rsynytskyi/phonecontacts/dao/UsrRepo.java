package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrRepo extends JpaRepository<Usr, Integer> {

    Usr findByName(String name);

    void deleteByName(String name);
}
