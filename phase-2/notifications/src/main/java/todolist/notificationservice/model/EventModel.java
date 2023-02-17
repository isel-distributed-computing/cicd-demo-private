package todolist.notificationservice.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
@Getter @Setter @ToString @NoArgsConstructor
public class EventModel {
    String id;
    String userName;
    String action;
}
