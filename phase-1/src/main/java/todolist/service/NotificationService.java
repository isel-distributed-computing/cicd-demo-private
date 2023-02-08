package todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import todolist.model.ToDoListItem;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    public void sendItemCreatedNotification(ToDoListItem item) {
        logger.info("Item created: " + item);
    }

    public void sendItemDeletedNotification(ToDoListItem item) {
        logger.info("Item deleted: " + item);
    }
}
