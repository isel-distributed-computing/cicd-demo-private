package todolist.service;

import java.util.concurrent.atomic.AtomicInteger;

public class ToDoListItem {

    private int id;
    private String description;
    private String username;

    public ToDoListItem() {}

    public ToDoListItem(int id, String username, String description) {
        this.id = id;
        this.username = username;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoListItem item = (ToDoListItem) o;
        return id == item.id;
    }


}
