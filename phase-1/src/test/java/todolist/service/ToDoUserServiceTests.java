package todolist.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ToDoUserServiceTests {
    @InjectMocks
    private ToDoUserService userService;

    @Test
    public void testRegisterUser() {
        userService.register("name", "pwd");
    }
}
