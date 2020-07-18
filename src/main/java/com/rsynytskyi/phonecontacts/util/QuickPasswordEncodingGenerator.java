package com.rsynytskyi.phonecontacts.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class QuickPasswordEncodingGenerator {

    public static void main(String[] args) {
        String password = "abc123";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        System.out.println(passwordEncoder.encode(password));

    }
}
