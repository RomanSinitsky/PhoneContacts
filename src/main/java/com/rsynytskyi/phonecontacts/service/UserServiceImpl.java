package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.dao.UsrRepo;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UsrRepo usrRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Usr updateUser(String name, Usr changedUsr) {
        Usr forUpdateUsr = usrRepo.findByName(name);
        forUpdateUsr.setName(changedUsr.getName());
        forUpdateUsr.setPassword(passwordEncoder.encode(changedUsr.getPassword()));
        return forUpdateUsr;
    }

    @Override
    public Usr deleteUser(String name) {
        usrRepo.deleteByName(name);
        Usr usr = new Usr();
        usr.setInfo("User successfully deleted");
        return usr;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usr> findAll() {
        List<Usr> all = usrRepo.findAll();
        return all;
    }
}
