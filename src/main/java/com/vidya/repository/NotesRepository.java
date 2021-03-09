package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
	
	List<Notes> findByLessonId(Long lesson_id);

}