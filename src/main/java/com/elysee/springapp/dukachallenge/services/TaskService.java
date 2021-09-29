package com.elysee.springapp.dukachallenge.services;


import com.elysee.springapp.dukachallenge.domain.Task;
import com.elysee.springapp.dukachallenge.domain.TaskOwner;
import com.elysee.springapp.dukachallenge.exceptions.TodoException;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    public Task saveTask(Task t);
    public void deleteTask(UUID id);
    public Task updateTask(Task todo) throws TodoException;
    public Task findById(UUID id);
    public List<Task> getAllTasks();
    public List<Task> getTaskByOwner(TaskOwner owner);
}
