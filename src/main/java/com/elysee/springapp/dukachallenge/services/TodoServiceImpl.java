package com.elysee.springapp.dukachallenge.services;

import com.elysee.springapp.dukachallenge.domain.Task;
import com.elysee.springapp.dukachallenge.exceptions.TodoException;
import com.elysee.springapp.dukachallenge.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TaskService {
    TaskRepository repository;

    public TodoServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * @param t
     * @return Task
     * */
    @Override
    public Task saveTask(Task t) {
        return repository.save(Task.builder()
                .id(UUID.randomUUID())
                .title(t.getTitle())
                .description(t.getDescription())
                .priority(t.getPriority())
                .ownedBy(t.getOwnedBy())
                .build()
        );
    }

    /**
     * @param id
     * @return void
     * */
    @Override
    public void deleteTask(UUID id) {
        repository.deleteById(id);
    }

    /**
     * @param t
     * @return Task
     * */
    @Override
    public Task updateTask(Task t) throws TodoException {
        Task todo = repository.findById(t.getId()).get();
        if(todo.equals(null)){
            throw new TodoException("Task to update not exist");
        }
        if(t.getTitle()!=null){
            todo.setTitle(t.getTitle());
        }
        if(t.getDescription()!=null){
            todo.setDescription(t.getDescription());
        }
        if(t.getPriority()!=null){
            todo.setPriority(t.getPriority());
        }

        Task updatedTodo = repository.save(todo);
        return  updatedTodo;
    }


    /**
     * @param id
     * @return Task
     * */
    @Override
    public Task findById(UUID id) {
        return repository.findById(id).get();
    }


    /**
     * @return List<Task>
     * */
    @Override
    public List<Task> getAllTasks() {
        List<Task> todos =  new ArrayList<>();
        repository.findAll().forEach(t -> todos.add(t));
        return todos;
    }
}

