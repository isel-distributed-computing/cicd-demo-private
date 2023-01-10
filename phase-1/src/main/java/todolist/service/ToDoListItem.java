package todolist.service;

import java.util.concurrent.atomic.AtomicInteger;

public class ToDoListItem {

    private int id;
    private String description;
    private String username;

    private static AtomicInteger atomicInt = new AtomicInteger();

    public ToDoListItem() {}

    public ToDoListItem(String username, String description) {
        id = atomicInt.addAndGet(1);
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
