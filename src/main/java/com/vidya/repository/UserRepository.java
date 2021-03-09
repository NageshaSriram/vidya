package com.vidya.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidya.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByEmailAndOrganizationId(String email, Long organization_id);

	boolean existsByUsernameAndOrganizationId(String username, Long organization_id);

	List<User> findByOrganizationId(Long organization_id);
	
	List<User> findByOrganizationIdAndRoleId(Long organization_id, Long role_id);
	
	List<User> findByOrganizationIdAndClassesId(Long organization_id, Long classes_id);
}
