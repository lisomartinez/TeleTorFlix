package com.teletorflix.app.controllers;

import com.teletorflix.app.model.Role;
import com.teletorflix.app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserFactory {

    public static Map.Entry<String, String> plainPasswordByValidUser() {
        return Map.entry("John", "123456");
    }

    public static User createValidUser() {
        return User.builder()
                .withUserName("John")
                .withPassword("123456")
                .withEmail("johnDow@gmail.com")
                .withRoles(new ArrayList<>(List.of(Role.USER)))
                .build();
    }
}
