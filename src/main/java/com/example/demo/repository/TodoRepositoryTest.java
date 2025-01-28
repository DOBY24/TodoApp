package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.TodoEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void saveTodo() {
        TodoEntry todo = new TodoEntry("Uvařit");
        TodoEntry savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo.getId());
        assertEquals("Uvařit", savedTodo.getDescription());
    }

    @Test
    public void deleteTodo(){
        TodoEntry todo = new TodoEntry("Uvařit");
        TodoEntry savedTodo = todoRepository.save(todo);
        todoRepository.deleteById(savedTodo.getId());
        List<TodoEntry> todos = StreamSupport
                .stream(todoRepository.findAll().spliterator(), false)
                .toList();

        assertTrue(todos.isEmpty());
    }

}
