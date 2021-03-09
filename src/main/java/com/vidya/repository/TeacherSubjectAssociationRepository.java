package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.TeacherSubjectAssociation;

@Repository
public interface TeacherSubjectAssociationRepository extends JpaRepository<TeacherSubjectAssociation, Long> {

	List<TeacherSubjectAssociation> findAllByUserIdIn(List<Long> user_id);

	boolean existsBySubjectId(Long subjectId);

}