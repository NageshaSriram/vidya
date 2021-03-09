package com.vidya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Classes;
import com.vidya.payload.ClassesPayload;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {

	boolean existsByNameAndOrganizationId(String name, Long organization_id);

	List<ClassesPayload> findByOrganizationId(Long organization_id);

}
