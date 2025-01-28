package com.example.demo.controller;

import com.example.demo.TodoEntry;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<TodoEntry> getAllTodos() {
        return StreamSupport
                .stream(todoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable Long id) {
        Optional<TodoEntry> todo = todoRepository.findById(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry todoEntry) {
        TodoEntry createdTodo = todoRepository.save(todoEntry);
        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntry> updateTodo(@PathVariable Long id, @RequestBody TodoEntry updatedTodo) {
        Optional<TodoEntry> existingTodo = todoRepository.findById(id);
        if (existingTodo.isPresent()) {
            TodoEntry todo = existingTodo.get();
            todo.setDescription(updatedTodo.getDescription());
            TodoEntry savedTodo = todoRepository.save(todo);
            return ResponseEntity.ok(savedTodo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
