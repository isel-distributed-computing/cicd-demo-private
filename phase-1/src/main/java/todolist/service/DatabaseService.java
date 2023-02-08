package todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.model.ToDo;
import todolist.model.User;
import todolist.repository.ToDoRepository;
import todolist.repository.UserRepository;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public ToDo saveToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public List<ToDo> findToDosByUser(User user) {
        return toDoRepository.findByUser(user);
    }
}
