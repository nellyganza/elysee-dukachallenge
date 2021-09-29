package com.elysee.springapp.dukachallenge.repository;

import com.elysee.springapp.dukachallenge.domain.Task;
import com.elysee.springapp.dukachallenge.domain.TaskOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findDistinctByOwnedBy(TaskOwner owner);
}
