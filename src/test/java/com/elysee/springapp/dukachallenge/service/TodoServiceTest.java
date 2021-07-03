package com.elysee.springapp.dukachallenge.service;


import com.elysee.springapp.dukachallenge.dbsetup.DbOperation;
import com.elysee.springapp.dukachallenge.dbsetup.setupDb;
import com.elysee.springapp.dukachallenge.domain.Priority;
import com.elysee.springapp.dukachallenge.domain.Task;
import com.elysee.springapp.dukachallenge.exceptions.TodoException;
import com.elysee.springapp.dukachallenge.services.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class TodoServiceTest extends setupDb {
    @Autowired
    TaskService todoService;


    @Test
    public void test_save_new_todo(){
        Task t = Task.builder()
                .id(UUID.randomUUID())
                .title("Eating")
                .description("I Will be Eating at 11:00")
                .priority(Priority.valueOf("HIGH"))
                .build();

        Task todo =  new Task();
        todo.setTitle("Coding");
        todo.setDescription("I have to write Java Code");
        todo.setPriority(Priority.valueOf("LOW"));
        Task savedTodo = todoService.saveTask(todo);
        assertThat(savedTodo.getTitle(),is(equalTo("Coding")));
    }

    @Test
    public void test_update_existing_todo_name() throws TodoException {
        Task todo = todoService.findById(UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"));
        todo.setTitle("Reading");
        Task updatedTodo = todoService.updateTask(todo);
        assertThat(updatedTodo.getTitle(),is(equalTo("Reading")));
    }

    @Test
    public void test_getAll_todos(){
        List<Task> todos = todoService.getAllTasks();
        assertThat(todos,not(equalTo(null)));
    }

    @Test
    public void test_findOne_Todo(){
        Task todo = todoService.findById(UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"));
        assertThat(todo.getTitle(),is(equalTo("Learning")));
    }

    @Test
    public void test_delete_todo(){
        todoService.deleteTask(UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"));
    }


    @BeforeEach
    public void initializeDb(){
        execute(DbOperation.INSERT_USER);
        execute(DbOperation.INSERT_TODO);
    }
    @AfterEach
    public void cleanDb(){
        execute(DbOperation.DELETE_TODO);
        execute(DbOperation.DELETE_USER);
    }
}
