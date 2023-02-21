package todolist.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import todolist.model.ToDo;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {TestConfigPostgreSQL.class})
public class ToDoRepositoryIntegrationTest {
    @Autowired
    private ToDoRepository todoRepository;

    @Test
    @Transactional
    void testFindByUsername() {
        ToDo todo = new ToDo("joe", "some todo");
        todoRepository.save(todo);

        List<ToDo> todoResult = todoRepository.findAllByUsername("joe");
        todo = todoResult.get(0);
        assertThat(todo.getUsername().equals("joe"));
        assertThat(todo.getDescription().equals("some todo"));
    }
}

