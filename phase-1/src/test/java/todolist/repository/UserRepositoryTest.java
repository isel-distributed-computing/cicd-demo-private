package todolist.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import todolist.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {TestConfigH2.class})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void testFindByUsername() {
        User user = new User("test_user", "abc");
        userRepository.save(user);

        Optional<User> userResult = userRepository.findByUsername("test_user");
        assertThat(userResult.isPresent());
        user = userResult.get();
        assertThat(user.getUsername().equals("test_user"));
        assertThat(user.getSaltedPwd().equals("test_password"));
    }
}

