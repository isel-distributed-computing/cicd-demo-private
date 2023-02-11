package todolist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfigH2.class})
public class ToDoRepositoryTest {
    @Autowired
    private ToDoRepository todoRepository;
}

