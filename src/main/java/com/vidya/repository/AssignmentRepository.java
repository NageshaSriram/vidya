package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	
	List<Assignment> findByLessonId(Long lesson_id);

}