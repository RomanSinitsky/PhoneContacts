package com.rsynytskyi.phonecontacts.controller;

import com.rsynytskyi.phonecontacts.model.Usr;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    private static final String RSYNYTSKY_APP = "RSYNYTSKY_APP";

    @GetMapping("/auth")
    public Usr auth(@AuthenticationPrincipal Usr usr) {
        String token = getJWTToken(RSYNYTSKY_APP, usr.getName());
        Usr user = new Usr();
        user.setId(usr.getId());
        user.setName(usr.getName());
        user.setToken(token);
        return user;
    }

    private String getJWTToken(String userid, String userName) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(userid)
                .setSubject(userName)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
