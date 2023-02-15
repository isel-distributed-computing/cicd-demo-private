package todolist.model;

import org.springframework.hateoas.RepresentationModel;
import todolist.controller.ToDoListController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ToDoListItemResource extends RepresentationModel<ToDoListItemResource> {
    private long id;
    private String description;
    private String username;

    public ToDoListItemResource() {}

    public ToDoListItemResource(long id, String username, String description) {
        this.id = id;
        this.username = username;
        this.description = description;
        add(linkTo(methodOn(ToDoListController.class).getToDoListItem(id, null)).withSelfRel());
    }

    public ToDoListItemResource(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getDescription() {
        return description;
    }
    public long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoListItemResource item = (ToDoListItemResource) o;
        return id == item.id && username.equals(item.username) && description.equals(item.description);
    }
    public String toString() {
        return "user: " + username + " / ToDo: " + description;
    }

}
