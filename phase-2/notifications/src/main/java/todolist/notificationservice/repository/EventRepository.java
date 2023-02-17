package todolist.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todolist.notificationservice.model.EventModel;

import java.util.List;

public interface EventRepository extends JpaRepository<EventModel, Long>  {
    List<EventModel> findAllByAction(String action);
}
