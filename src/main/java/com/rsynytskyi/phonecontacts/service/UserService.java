package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.model.Usr;

import java.util.List;

public interface UserService {

    Usr updateUser(String name, Usr usr);

    Usr deleteUser(String name);

    List<Usr> findAll();

    Usr findByName(String userName);
}
