package com.agendo.frontoffice_planning.repository;
import com.agendo.frontoffice_planning.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>  {
}
