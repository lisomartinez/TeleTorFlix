package com.teletorflix.app.service.authentication;

import com.teletorflix.app.model.Role;
import com.teletorflix.app.model.User;
import com.teletorflix.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String P_TOKEN = "";

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found. " + username));
        List<Role> roles = user.getRoles();
        roles.add(Role.AUTHENTICATED);
        return this.userBuilder(user.getUsername(), user.getPassword(), roles, user.isActive());
    }

    private UserDetails userBuilder(String username, String password, List<Role> roles, Boolean active) {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(Role::roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        return new org.springframework.security.core.userdetails.User(username, password, active,
                true, true, true, authorities);
    }
}
