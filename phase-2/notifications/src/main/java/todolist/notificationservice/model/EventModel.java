package todolist.notificationservice.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity
@EnableAutoConfiguration
@Table(name = "eventlog")
public class EventModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    String id;
    String userName;
    String action;
}
