package todolist.service;

// ToDoListService.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.model.ToDo;
import todolist.model.ToDoListItemResource;
import todolist.model.User;
import todolist.repository.ToDoRepository;
import todolist.repository.UserRepository;

import java.util.*;

@Service
public class ToDoListService {
    private static final Logger logger = LoggerFactory.getLogger(ToDoListService.class);

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ToDoRepository toDoListRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<ToDoListItemResource> deleteToDoListItem(long itemId) {
        logger.info("ToDo item deleted");
        ToDo item = toDoListRepository.getReferenceById(itemId);
        if (item == null) return Optional.empty();
        ToDoListItemResource toDoListItemResource = new ToDoListItemResource(item.getId(), item.getUser().getUsername(), item.getDescription());
        toDoListRepository.deleteById(itemId);
        notificationService.sendItemDeletedNotification(toDoListItemResource);
        return Optional.ofNullable(toDoListItemResource);
    }

    public ToDoListItemResource createToDoListItem(String username, String description) throws UnknownUserException {
        // Validate the input and create a new to-do list item
        ToDoListItemResource item = new ToDoListItemResource(username, description);
        // Save the item to the database
        ToDo todo = saveToDoListItem(item);
        item.setId(todo.getId());
        // Send a notification
        notificationService.sendItemCreatedNotification(item);
        return item;
    }

    private ToDo saveToDoListItem(ToDoListItemResource item) throws UnknownUserException {
        logger.info("Save ToDo item");
        Optional<User> user = userRepository.findByUsername(item.getUsername());
        if (user.isEmpty()) throw new UnknownUserException("User not found"); // TODO: revise exception type
        ToDo toDo = new ToDo(user.get(), item.getDescription());
        return toDoListRepository.save(toDo);
    }

    public Optional<ToDoListItemResource> getToDoListItem(long itemId) {
        logger.info("Get ToDo list item");
        ToDo item = toDoListRepository.getReferenceById(itemId);
        return Optional.ofNullable(new ToDoListItemResource(item.getId(), item.getUser().getUsername(), item.getDescription()));
    }

    public Optional<List<ToDoListItemResource>> getToDoListItemList(String username) {
        logger.info("Get ToDo list of all item by user");
        List<ToDoListItemResource> allItems = new ArrayList<>();
        User user = userRepository.getReferenceByUsername(username);
        toDoListRepository.findAllByUser(user).forEach(item -> {
            allItems.add(new ToDoListItemResource(item.getId(), item.getUser().getUsername(), item.getDescription()));
        });
        return Optional.ofNullable(allItems);
    }
}

