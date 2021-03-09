package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Discussion;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

	List<Discussion> findByLessonId(Long lesson_id);

}