package com.vidya.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findByName(String name);

	boolean existsById(Long id);
	
	boolean existsByName(String name);

	void deleteById(Long id);

	List<Organization> findAll();

	List<Organization> findAll(Sort sort);
}
