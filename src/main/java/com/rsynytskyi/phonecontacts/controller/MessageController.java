package com.rsynytskyi.phonecontacts.controller;

import com.rsynytskyi.phonecontacts.dao.MessageRepo;
import com.rsynytskyi.phonecontacts.dao.UsrRepo;
import com.rsynytskyi.phonecontacts.model.Message;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/messages")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UsrRepo usrRepo;

    @PostMapping
    public void addMessage(@RequestBody Message message) {
        Usr usr = message.getUsr();
        messageRepo.save(message);
    }

}