package todolist.service;

public interface INotificationStrategy {
    void sendCreateNotification(ToDoListItem item);
    void sendDeleteNotification(ToDoListItem item);
}
