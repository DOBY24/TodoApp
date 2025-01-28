package com.example.demo.repository;

import com.example.demo.TodoEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<TodoEntry,Long> {
}


