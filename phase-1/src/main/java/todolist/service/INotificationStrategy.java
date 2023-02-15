package todolist.service;

import todolist.model.ToDoListItemResource;

public interface INotificationStrategy {
    void sendCreateNotification(ToDoListItemResource item);
    void sendDeleteNotification(ToDoListItemResource item);
}
