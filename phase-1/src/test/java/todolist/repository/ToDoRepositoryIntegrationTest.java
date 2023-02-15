/*
package todolist.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import todolist.model.ToDo;
import todolist.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@EnabledIfSystemProperty(named = "dbtype", matches = "postgresql")
@SpringBootTest
@ContextConfiguration(classes = {TestConfigPostgreSQL.class})
public class ToDoRepositoryIntegrationTest {
    @Autowired
    private ToDoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testFindByUsername() {
        User testUser = new User("test_user", "abc");
        userRepository.save(testUser);
        ToDo todo = new ToDo(testUser, "some todo");
        todoRepository.save(todo);

        List<ToDo> todoResult = todoRepository.findAllByUser(testUser);
        todo = todoResult.get(0);
        assertThat(todo.getUser().getUsername().equals("test_user"));
        assertThat(todo.getDescription().equals("some todo"));
    }
}

*/
