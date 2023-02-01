package todolist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceTests {

    @Mock
    private ToDoUserService userService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ToDoListService toDoListService;

    @Test
    public void testCreateToDoListItem() {
        String username = "testuser";
        String description = "Test description";

        ToDoListItem item = toDoListService.createToDoListItem(username, description);

        assertEquals(1, item.getId());
        assertEquals(username, item.getUsername());
        assertEquals(description, item.getDescription());

        verify(notificationService, times(1)).sendItemCreatedNotification(item);
    }

    @Test
    public void testDeleteToDoListItem() {
        //ToDoListItem item = new ToDoListItem(itemId, "testuser", "Test description");
        ToDoListItem item = toDoListService.createToDoListItem("testuser", "test description");
        Optional<ToDoListItem> deletedItem = toDoListService.deleteToDoListItem(item.getId());

        assertTrue(deletedItem.isPresent());
        verify(notificationService, times(1)).sendItemDeletedNotification(item);
    }

    @Test
    public void testGetToDoListItem() {
        int itemId = 1;
        //ToDoListItem item = new ToDoListItem(itemId, "testuser", "Test description");
        ToDoListItem item = toDoListService.createToDoListItem("test user", "test description");
        Optional<ToDoListItem> foundItem = toDoListService.getToDoListItem(item.getId());

        assertTrue(foundItem.isPresent());
        assertEquals(itemId, foundItem.get().getId());
    }

    @Test
    public void testGetToDoListItemList() {
        String username = "test user";
        ToDoListItem item1 = new ToDoListItem(1, username, "Test description 1");
        ToDoListItem item2 = new ToDoListItem(2, username, "Test description 2");
        toDoListService.createToDoListItem(username, "test description 1");
        toDoListService.createToDoListItem(username, "test description 2");

        Optional<List<ToDoListItem>> itemList = toDoListService.getToDoListItemList(username);
        assertTrue(itemList.isPresent());
        assertEquals(2, itemList.get().size());
        assertEquals(item1, itemList.get().get(0));
        assertEquals(item2, itemList.get().get(1));
    }
}
