package com.teletorflix.app.repository;

import com.teletorflix.app.model.Role;
import com.teletorflix.app.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void findUserByUsername_ShouldReturnUser() {
        User user = User.builder()
                .withUserName("John")
                .withPassword("123456")
                .withEmail("johnDow@gmail.com")
                .withRoles(List.of(Role.USER))
                .build();

        Optional<User> result = userRepository.findByUsername("John");
        assertThat(result.get()).isEqualTo(user);
    }
}
