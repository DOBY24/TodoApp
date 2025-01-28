package com.example.demo;

import jakarta.persistence.*;

@Entity
public class TodoEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_entry_seq_generator")
    @SequenceGenerator(name = "todo_entry_seq_generator", sequenceName = "TODO_ENTRY_SEQ", allocationSize = 1)

    private Long id;
    private String description;

    public TodoEntry(String description){
        this.description = description;
    }

    public TodoEntry() {

    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
