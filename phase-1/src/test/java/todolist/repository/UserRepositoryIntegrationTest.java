package todolist.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import todolist.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIfSystemProperty(named = "dbtype", matches = "postgresql")
@SpringBootTest
@ContextConfiguration(classes = {TestConfigPostgreSQL.class})
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testFindByUsername() {
        User user = new User("test_user", "abc");
        userRepository.save(user);

        Optional<User> userResult = userRepository.findByUsername("test_user");
        assertThat(userResult.isPresent());
        user = userResult.get();
        assertThat(user.getUsername().equals("test_user"));
        assertThat(user.getSaltedPwd().equals("test_password"));
    }

    @Test
    @Transactional
    void testGetReferenceByUsername() {
        User user = new User("test_user", "abc");
        userRepository.save(user);

        User userResult = userRepository.getReferenceByUsername("test_user");
        assertThat(userResult.getUsername().equals("test_user"));
        assertThat(userResult.getSaltedPwd().equals("test_password"));
    }
}

