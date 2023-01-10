package todolist.controller;
// ToDoListController.java

import todolist.service.ToDoListItem;
import todolist.service.ToDoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todolist")
public class ToDoListController {
    private static final Logger logger = LoggerFactory.getLogger(ToDoListController.class);
    private final ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping()
    public ToDoListItem createToDoListItem(@RequestBody CreateToDoListItemRequest request) {
        logger.info("Create todo list item");
        ToDoListItem item = toDoListService.createToDoListItem(request.getUsername(), request.getDescription());
        return item;
    }

    @DeleteMapping("/{itemId}")
    public ToDoListItem deleteToDoListItem(@PathVariable("itemId") int itemId) {
        logger.info("Delete todo list item");
        Optional<ToDoListItem> item = toDoListService.deleteToDoListItem(itemId);
        if (!item.isEmpty()) {
            return item.get();
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping("/{username}")
    public List<ToDoListItem> getAllItemsByUser(@PathVariable("username") String username) {
        logger.info("Get all items from user");
        Optional<List<ToDoListItem>> list = toDoListService.getToDoListItemList(username);
        if (!list.isEmpty())
            return list.get();
        return new ArrayList<>();
    }

    //...
}

