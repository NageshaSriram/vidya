package com.vidya.repository;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vidya.model.AssignmentSubmission;

@Repository
public interface AssignmentSumbissionRepository extends JpaRepository<AssignmentSubmission, Long> {

	List<AssignmentSubmission> findByAssignmentId(Long assignment_id);

	AssignmentSubmission findByAssignmentIdAndUserId(Long assignment_id, Long user_id);

	@Modifying
	@Query("UPDATE AssignmentSubmission assignSub SET assignSub.document = ?1 WHERE assignSub.id = ?2")
	void updateDocumentById(String documentFilename, @Valid @NotNull Long submissionId);

	@Modifying
	@Query("UPDATE AssignmentSubmission assignSub SET assignSub.feedback = ?1 WHERE assignSub.id = ?2")
	void assignmentCorrectionById(String feedback, @Valid @NotNull Long submissionId);

}