package com.example.rest.api.repository;

import com.example.rest.api.model.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
    Assignee findAssigneeById(Long id);
    Assignee findAssigneeByName(String name);

    @Query("SELECT DISTINCT a FROM Assignee a")
    List<Assignee> AllAssignees();
}
