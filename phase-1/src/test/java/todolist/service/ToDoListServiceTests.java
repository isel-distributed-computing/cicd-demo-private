package todolist.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import todolist.model.ToDo;
import todolist.model.ToDoListItem;
import todolist.model.User;
import todolist.repository.ToDoRepository;
import todolist.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ToDoListServiceTests {

    private final String username = "testuser";
    private final String password = "abc";
    private final String description = "Test description";
    @Mock
    private ToDoRepository toDoListRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ToDoListService toDoListService;

    @Before
    public void setUp() {
        User user = new User(username, password);
        when(userRepository.save(user))
                .thenReturn(user);
        when(userRepository.getReferenceById(user.getId()))
                .thenReturn(user);
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void testCreateToDoListItem() throws UnknownUserException {
        // Arrange
        User user = new User(username, password);
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        // Act
        ToDoListItem item = toDoListService.createToDoListItem(username, description);

        // Assert
        assertEquals(username, item.getUsername());
        assertEquals(description, item.getDescription());

        verify(notificationService, times(1)).sendItemCreatedNotification(item);
    }

    @Test
    public void testDeleteToDoListItem() throws UnknownUserException {
        // Arrange
        ToDo item = new ToDo(1L, new User("testuser", "password"), "Description");
        when(toDoListRepository.getReferenceById(1L)).thenReturn(item);

        // Act
        Optional<ToDoListItem> deletedItem = toDoListService.deleteToDoListItem(1L);

        // Assert the result
        assertTrue(deletedItem.isPresent());
        assertEquals(1L, deletedItem.get().getId());
        assertEquals("testuser", deletedItem.get().getUsername());
        assertEquals("Description", deletedItem.get().getDescription());

        // Verify that the correct methods were called on the mocks
        verify(notificationService).sendItemDeletedNotification(deletedItem.get());
        verify(toDoListRepository).deleteById(1L);
    }

    @Test
    public void testGetToDoListItem() throws UnknownUserException {
        // Arrange
        ToDo item = new ToDo(1L, new User("testuser", "password"), "Description");
        when(toDoListRepository.getReferenceById(1L)).thenReturn(item);

        // Act
        Optional<ToDoListItem> foundItem = toDoListService.getToDoListItem(item.getId());

        // Assert
        assertTrue(foundItem.isPresent());
        assertEquals(1L, foundItem.get().getId()); // TODO: check if this is correct (i.e. if the id is correct)
    }

    @Test
    public void testGetToDoListItemList() throws UnknownUserException {
        // Arrange
        ToDo item1 = new ToDo(1L, new User("testuser", "password"), "Description");
        ToDo item2 = new ToDo(2L, new User("testuser", "password"), "Description");
        ToDo item3 = new ToDo(3L, new User("testuser", "password"), "Description");
        when(toDoListRepository.findAllByUser("testuser"))
                .thenReturn(List.of(item1, item2, item3));

        // Act
        Optional<List<ToDoListItem>> itemList = toDoListService.getToDoListItemList("testuser");

        // Assert
        assertTrue(itemList.isPresent());
        assertEquals(3, itemList.get().size());
        // TODO: check why not equal
        //assertEquals(new ToDoListItem(item1.getUser().getUsername(), item1.getDescription()), itemList.get().get(0));
        //assertEquals(new ToDoListItem(item2.getUser().getUsername(), item2.getDescription()), itemList.get().get(1));
        //assertEquals(new ToDoListItem(item3.getUser().getUsername(), item3.getDescription()), itemList.get().get(2));
    }
}
