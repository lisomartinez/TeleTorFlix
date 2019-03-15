package com.teletorflix.app.controllers;

import com.teletorflix.app.model.User;
import com.teletorflix.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Role_ADMIN')")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
