package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vidya.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByFeedbackTo(Long feedback_to);

	@Modifying
	@Query(value = "update userfeedback set feedback =?1, rating = ?2 where id = ?3", nativeQuery = true)
	void updateFeedbackById(String feedback, Double rating, Long id);

}