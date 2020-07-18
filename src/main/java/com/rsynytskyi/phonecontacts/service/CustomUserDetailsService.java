package com.rsynytskyi.phonecontacts.service;

import com.rsynytskyi.phonecontacts.dao.UsrRepo;
import com.rsynytskyi.phonecontacts.model.Usr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsrRepo usrRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usr byName = usrRepo.findByName(name);
        return byName;
    }
}
