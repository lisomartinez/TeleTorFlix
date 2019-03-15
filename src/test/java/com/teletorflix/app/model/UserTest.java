package com.teletorflix.app.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void userBuilder_withoutActive_ShouldReturnInstanceWithActiveEqualsToTrue() {
        User user = User.builder()
                .withUserName("John")
                .withPassword("123456")
                .withEmail("johnDow@gmail.com")
                .withRegistrationDate(LocalDateTime.now())
                .withRoles(List.of(Role.USER))
                .build();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void userBuilder_withoutRegistrationDate_ShouldReturnInstanceWithRegistrationDateEqualsToLocalDateTimeNow() {
        User user = User.builder()
                .withUserName("John")
                .withPassword("123456")
                .withEmail("johnDow@gmail.com")
                .withActive(true)
                .withRoles(List.of(Role.USER))
                .build();
        assertThat(user.getRegistrationDate()).isNotNull();
    }

    @Test
    void userBuilder_withPassword_shouldReturnInstanceWithPasswordEncripted() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        final String password = "123456";

        User user = User.builder()
                .withUserName("John")
                .withPassword(password)
                .withEmail("johnDow@gmail.com")
                .withActive(true)
                .withRoles(List.of(Role.USER))
                .build();
        assertThat(encoder.matches(password, user.getPassword())).isTrue();
    }

    @Test
    void userBuilder_shouldReturnInstance() {
        User user = User.builder()
                .withUserName("John")
                .withPassword("123456")
                .withEmail("johnDow@gmail.com")
                .withRoles(List.of(Role.USER))
                .build();

        assertThat(user.getUsername()).isEqualTo("John");
        assertThat(user.getEmail()).isEqualTo("johnDow@gmail.com");
        assertThat(user.getRoles()).isEqualTo(List.of(Role.USER));
    }
}