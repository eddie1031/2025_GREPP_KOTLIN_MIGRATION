package com.grepp.curdsample.dao;

import com.grepp.curdsample.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByCode(String code);

    @Query("select t from Task t where t.endTime = CURRENT_DATE order by t.priority desc limit 10")
    List<Task> findTenTasksDueToToday();
}
