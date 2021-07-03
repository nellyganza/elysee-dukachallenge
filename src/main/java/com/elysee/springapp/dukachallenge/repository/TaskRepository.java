package com.elysee.springapp.dukachallenge.repository;

import com.elysee.springapp.dukachallenge.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
