package todolist.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import todolist.model.ToDoListItem;
import todolist.model.User;
import todolist.repository.ToDoRepository;
import todolist.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoListServiceTests {

    private final String username = "testuser";
    private final String password = "secure password";
    private final String description = "Test description";
    @Mock
    private ToDoRepository toDoListRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ToDoUserService userService;

    @InjectMocks
    private ToDoListService toDoListService;

    @Before
    public void setUp() {
        User user = new User(username, password, "dummy salt");
        when(userRepository.save(user))
                .thenReturn(user);
        when(userRepository.getReferenceById(user.getId()))
                .thenReturn(user);
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void testCreateToDoListItem() {
        // Arrange
        User user = new User(username, password, "dummy salt");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        // Act
        ToDoListItem item = toDoListService.createToDoListItem(username, description);

        // Assert
        assertEquals(1, item.getId());
        assertEquals(username, item.getUsername());
        assertEquals(description, item.getDescription());

        verify(notificationService, times(1)).sendItemCreatedNotification(item);
    }

    @Test
    public void testDeleteToDoListItem() {
        // Arrange
        User user = new User(username, password, "dummy salt");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        ToDoListItem item = toDoListService.createToDoListItem("testuser", "testpassword");

        // Act
        Optional<ToDoListItem> deletedItem = toDoListService.deleteToDoListItem(item.getId());

        // Assert
        assertTrue(deletedItem.isPresent());
        verify(notificationService, times(1)).sendItemDeletedNotification(item);
    }

    @Test
    public void testGetToDoListItem() {
        // Arrange
        int itemId = 1;
        //ToDoListItem item = new ToDoListItem(itemId, "testuser", "Test description");
        User user = new User(username, password, "dummy salt");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        ToDoListItem item = toDoListService.createToDoListItem(username, description);

        // Act
        Optional<ToDoListItem> foundItem = toDoListService.getToDoListItem(item.getId());

        // Assert
        assertTrue(foundItem.isPresent());
        assertEquals(itemId, foundItem.get().getId());
    }

    @Test
    public void testGetToDoListItemList() {
        // Arrange
        User user = new User(username, password, "dummy salt");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        ToDoListItem item1 = new ToDoListItem(1, username, "Test description 1");
        ToDoListItem item2 = new ToDoListItem(2, username, "Test description 2");
        toDoListService.createToDoListItem(username, "test description 1");
        toDoListService.createToDoListItem(username, "test description 2");

        // Act
        Optional<List<ToDoListItem>> itemList = toDoListService.getToDoListItemList(username);

        // Assert
        assertTrue(itemList.isPresent());
        assertEquals(2, itemList.get().size());
        assertEquals(item1, itemList.get().get(0));
        assertEquals(item2, itemList.get().get(1));
    }
}
