package com.vidya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vidya.model.SemesterMarks;

@Repository
public interface SemesterMarksRepository extends JpaRepository<SemesterMarks, Long> {

	@Modifying
	@Query("UPDATE SemesterMarks sm SET sm.total_marks = ?2, sm.obtained_marks = ?3, sm.percentage = ?4 WHERE sm.id = ?1")
	void updateSemesterMarks(Long id, Integer total_marks, Double obtained_marks, Double percentage);
}
