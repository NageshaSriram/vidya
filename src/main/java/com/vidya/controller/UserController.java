package com.vidya.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.enums.RoleName;
import com.vidya.model.Subject;
import com.vidya.model.TeacherSubjectAssociation;
import com.vidya.model.User;
import com.vidya.payload.request.UserSummary;
import com.vidya.payload.response.SubjectResponse;
import com.vidya.payload.response.TeacherSubjectAssociationResponse;
import com.vidya.payload.response.UserResponse;
import com.vidya.repository.OrganizationRepository;
import com.vidya.repository.RoleRepository;
import com.vidya.repository.TeacherSubjectAssociationRepository;
import com.vidya.repository.UserRepository;
import com.vidya.security.CurrentUser;
import com.vidya.security.UserPrincipal;
import com.vidya.util.AppConstants;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	TeacherSubjectAssociationRepository teacherSubjectAssociationRepository;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		logger.info("Fetching user summary");
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	@GetMapping("/all/organization/{orgId}/role/{roleId}")
	public ResponseEntity<?> getUsersByRoleId(@Valid @PathVariable("orgId") @NotNull Long orgId,
			@Valid @PathVariable("roleId") @NotNull Long roleId) {
		try {
			if (!organizationRepository.existsById(orgId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			if (!roleRepository.existsById(roleId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<UserResponse> users = userRepository.findByOrganizationIdAndRoleId(orgId, roleId).stream()
					.map(user -> {
						return new UserResponse(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
								user.getOrganization().getId(), user.getRole().getId(), user.getClasses().getId());
					}).collect(Collectors.toList());
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/teachers/all/{orgId}")
	public ResponseEntity<?> getAllTeaches(@Valid @PathVariable("orgId") @NotNull Long orgId) {
		try {
			if (!organizationRepository.existsById(orgId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			Long roleId = RoleName.TEACHER.getIndex();
			List<Long> teachers = userRepository.findByOrganizationIdAndRoleId(orgId, roleId).stream().map(User::getId)
					.collect(Collectors.toList());
			Map<UserResponse, List<SubjectResponse>> asscosications = new HashMap<>();
			List<TeacherSubjectAssociation> teacherSubjectAssociations = teacherSubjectAssociationRepository
					.findAllByUserIdIn(teachers);
			teacherSubjectAssociations.forEach(association -> {
				User user = association.getUser();
				UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getName(),
						user.getEmail(), user.getOrganization().getId(), user.getRole().getId(),
						user.getClasses().getId());

				Subject subject = association.getSubject();

				SubjectResponse subjectResponse = new SubjectResponse(subject.getId(), subject.getName(),
						subject.getClasses().getId());
				if (asscosications.containsKey(userResponse)) {
					List<SubjectResponse> subList = new ArrayList<>();
					subList.addAll(asscosications.get(userResponse));
					subList.add(subjectResponse);
					asscosications.put(userResponse, subList);
				} else {
					asscosications.put(userResponse, Arrays.asList(subjectResponse));
				}
			});
			List<TeacherSubjectAssociationResponse> users = asscosications.keySet().stream().map(association -> {
				return new TeacherSubjectAssociationResponse(association, asscosications.get(association));
			}).collect(Collectors.toList());
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
}
