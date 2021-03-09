package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	List<Subject> findByClassesId(Long classes_id);
	
	boolean existsByNameAndClassesId(String name, Long classes_id);
}
