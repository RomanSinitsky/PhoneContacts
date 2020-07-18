package com.rsynytskyi.phonecontacts.controller;

import com.rsynytskyi.phonecontacts.dao.UsrRepo;
import com.rsynytskyi.phonecontacts.model.Roles;
import com.rsynytskyi.phonecontacts.model.Usr;
import com.rsynytskyi.phonecontacts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorizationController {

    @Autowired
    private UsrRepo usrRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Usr auth(@AuthenticationPrincipal Usr usr) {
        usr.setToken("token");
        return usr;
    }

    @ResponseBody
    @GetMapping("/logout")
    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public Usr register(@RequestBody Usr usr) {
        Usr byName = usrRepo.findByName(usr.getName());
        if (byName != null) {
            byName.setPassword("Not Displayable");
            byName.setInfo("User already exists");
            return byName;
        }
        else {
            usr.setActive(true);
            usr.setPassword(passwordEncoder.encode(usr.getPassword()));
            usr.setRolesSet(Collections.singleton(Roles.USER));
            usrRepo.save(usr);
            usr.setPassword("Not displayable");
            return usr;
        }
    }

    @ResponseBody
    @PostMapping(path = "user/{name}")
    public Usr updateUser(@PathVariable String name, @RequestBody Usr usr) {
        Usr updatedUsr = userService.updateUser(name, usr);
        updatedUsr.setPassword("Not displayable");
        return updatedUsr;
    }

    @ResponseBody
    @GetMapping(path = "user/{name}/delete")
    public void deleteUser(@PathVariable String name) {
        userService.deleteUser(name);
    }

    @ResponseBody
    @GetMapping(path = "/allUsers")
    List<Usr> getAllUsers() {
        List<Usr> all = userService.findAll().stream().peek(u -> u.setPassword("Not displayable"))
                .collect(Collectors.toList());
        return all;
    }
}
