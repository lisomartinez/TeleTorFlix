package com.teletorflix.app.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "Public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<Role> roles;

    public User() {
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public User(int id, String username, String password, String email, boolean active, LocalDateTime registrationDate, List<Role> roles) {
        this(username, password, email, active, registrationDate, roles);
        this.id = id;
    }

    public User(String username, String password, String email, boolean active, LocalDateTime registrationDate, List<Role> roles) {
        this.username = username;
        setPassword(password);
        this.email = email;
        this.registrationDate = registrationDate;
        this.active = active;
        this.roles = roles;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            this.password = UUID.randomUUID().toString();
        } else {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", roles=" + roles.toString() +
                '}';
    }

    public static class UserBuilder {
        private Integer id = null;
        private String username;
        private String password;
        private String email = null;
        private boolean active = true;
        private LocalDateTime registrationDate = LocalDateTime.now();
        private List<Role> roles = null;

        public UserBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUserName(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public UserBuilder withRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public UserBuilder withRoles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            if (this.id == null) {
                return new User(username, password, email, active, registrationDate, roles);
            } else {
                return new User(id, username, password, email, active, registrationDate, roles);
            }
        }
    }
}
