package todolist.service;

// ToDoListService.java
import org.apache.catalina.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.model.ToDo;
import todolist.model.ToDoListItem;
import todolist.model.User;
import todolist.repository.ToDoRepository;
import todolist.repository.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ToDoListService {
    private static final Logger logger = LoggerFactory.getLogger(ToDoListService.class);
    private final ToDoUserService userService;
    private final NotificationService notificationService;
    private HashMap<String, List<ToDoListItem>> allItems = new HashMap<>();

    private AtomicInteger atomicInt = new AtomicInteger(0);
    @Autowired
    private ToDoRepository toDoListRepository;
    @Autowired
    private UserRepository userRepository;


    public ToDoListService(
            ToDoUserService userService,
            NotificationService notificationService,
            ToDoRepository toDoListRepository,
            UserRepository userRepository) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.toDoListRepository = toDoListRepository;
        this.userRepository = userRepository;
    }

    public ToDoListItem createToDoListItem(String username, String description) {
        // Validate the input and create a new to-do list item
        ToDoListItem item = new ToDoListItem(atomicInt.addAndGet(1), username, description);
        // Save the item to the database
        saveToDoListItem(item);
        // Send a notification
        notificationService.sendItemCreatedNotification(item);
        return item;
    }

    private void saveToDoListItem(ToDoListItem item) {
        logger.info("Save ToDo item");
        // In-memory DB
        List<ToDoListItem> list = allItems.get(item.getUsername());
        if (list==null) {
            list = new ArrayList<>();
            list.add(item);
            allItems.put(item.getUsername(), list);
        } else {
            list.add(item);
        }
        // real DB
        Optional<User> user = userRepository.findByUsername(item.getUsername());
        if (user.isEmpty()) throw new RuntimeException("User not found"); // TODO: revise exception type
        ToDo toDo = new ToDo(user.get(), item.getDescription());
        toDoListRepository.save(toDo);
    }

    public Optional<ToDoListItem> deleteToDoListItem(int itemId) {
        ToDoListItem item = null;
        for (List<ToDoListItem> list : allItems.values()) {
            Iterator<ToDoListItem> iter = list.iterator();
            while (iter.hasNext()) {
                item = iter.next();
                if (item.getId() == itemId) {
                    iter.remove();
                    break;
                }
            }
        }
        if (item != null) {
            notificationService.sendItemDeletedNotification(item);
        }
        logger.info("ToDo item deleted");
        return Optional.ofNullable(item);
    }

    public Optional<ToDoListItem> getToDoListItem(int itemId) {
        logger.info("Get ToDo list item");
        ToDoListItem foundItem = null;
        for (List<ToDoListItem> list : allItems.values()) {
            for (ToDoListItem item : list) {
                if (item.getId() == itemId) {
                    foundItem = item;
                    break;
                }
            }
        }
        return Optional.ofNullable(foundItem);
    }

    public Optional<List<ToDoListItem>> getToDoListItemList(String username) {
        logger.info("Get ToDo list of all item by user");
        return Optional.ofNullable(allItems.get(username));
    }
}

