package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsByNameAndSubjectId(String name, Long subject_id);
	
	List<Book> findBySubjectId(Long subject_id);

}