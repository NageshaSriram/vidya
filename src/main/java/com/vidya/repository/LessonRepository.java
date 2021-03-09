package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
	
	boolean existsByNameAndBookId(String name, Long book_id);
	
	List<Lesson> findByBookId(Long book_id);
	

}