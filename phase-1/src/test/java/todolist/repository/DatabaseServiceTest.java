/*
package todolist.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import todolist.model.ToDo;
import todolist.model.User;
import todolist.service.DatabaseService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {TestConfigDB.class})
public class DatabaseServiceTest {

    @Autowired
    private DatabaseService databaseService;

    @BeforeEach
    public void setUp() {
        // Create test data
        User user = new User("test_user", "password", "salt");
        ToDo toDo1 = new ToDo(user, "Buy groceries");
        ToDo toDo2 = new ToDo(user, "Walk the dog");

        // Save test data to the database
        databaseService.saveUser(user);
        databaseService.saveToDo(toDo1);
        databaseService.saveToDo(toDo2);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test data
        databaseService.deleteAll();
    }

    @Test
    public void testSaveUser() {
        // Test the saveUser() method
        databaseService.saveUser(new User("test_user2", "password", "salt"));
    }

    @Test
    public void testGetUser() {
        // Test the getUser() method
        // Create a user
        User user = new User("johndoe", "password", "salt");
        databaseService.saveUser(user);
        ToDo toDo = new ToDo(user, "Buy groceries");
        databaseService.saveToDo(toDo);

        // Get the user from the database
        List<ToDo> retrievedUser = databaseService.findToDosByUser(user);

        // Verify that the user was retrieved
        assertEquals(true, retrievedUser.size()==1);
    }
}

*/
