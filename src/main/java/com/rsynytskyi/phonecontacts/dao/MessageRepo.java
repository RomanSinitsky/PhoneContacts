package com.rsynytskyi.phonecontacts.dao;

import com.rsynytskyi.phonecontacts.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Integer> {
}

