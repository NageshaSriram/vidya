package com.vidya.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidya.enums.RoleName;
import com.vidya.model.Assignment;
import com.vidya.model.Book;
import com.vidya.model.Classes;
import com.vidya.model.Discussion;
import com.vidya.model.Lesson;
import com.vidya.model.Notes;
import com.vidya.model.Organization;
import com.vidya.model.Role;
import com.vidya.model.Semester;
import com.vidya.model.SemesterMarks;
import com.vidya.model.Subject;
import com.vidya.model.TeacherSubjectAssociation;
import com.vidya.model.User;
import com.vidya.payload.ClassesPayload;
import com.vidya.payload.request.AssignmentRequest;
import com.vidya.payload.request.BookRequest;
import com.vidya.payload.request.DiscussionRequest;
import com.vidya.payload.request.LessonRequest;
import com.vidya.payload.request.NotesRequest;
import com.vidya.payload.request.OrganizationRequest;
import com.vidya.payload.request.RoleRequest;
import com.vidya.payload.request.SemesterMarksRequest;
import com.vidya.payload.request.SemesterRequest;
import com.vidya.payload.request.SignUpRequest;
import com.vidya.payload.request.SubjectRequest;
import com.vidya.payload.response.ApiResponse;
import com.vidya.payload.response.AssignmentResponse;
import com.vidya.payload.response.BookResponse;
import com.vidya.payload.response.DiscussionResponse;
import com.vidya.payload.response.LessonResponse;
import com.vidya.payload.response.NotesResponse;
import com.vidya.payload.response.OrganizationResponse;
import com.vidya.payload.response.RoleResponse;
import com.vidya.payload.response.SemesterResponse;
import com.vidya.payload.response.SubjectResponse;
import com.vidya.payload.response.UserResponse;
import com.vidya.repository.AssignmentRepository;
import com.vidya.repository.BookRepository;
import com.vidya.repository.ClassesRepository;
import com.vidya.repository.DiscussionRepository;
import com.vidya.repository.LessonRepository;
import com.vidya.repository.NotesRepository;
import com.vidya.repository.OrganizationRepository;
import com.vidya.repository.RoleRepository;
import com.vidya.repository.SemesterMarksRepository;
import com.vidya.repository.SemesterRepository;
import com.vidya.repository.SubjectRepository;
import com.vidya.repository.TeacherSubjectAssociationRepository;
import com.vidya.repository.UserRepository;
import com.vidya.service.UserService;
import com.vidya.util.AppConstants;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ClassesRepository classesRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	NotesRepository notesRepository;

	@Autowired
	AssignmentRepository assignmentRepository;

	@Autowired
	DiscussionRepository discussionRepository;

	@Autowired
	TeacherSubjectAssociationRepository teacherSubjectAssociationRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	SemesterMarksRepository semesterMarksRepository;

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping("/organization/add/organization")
	public ResponseEntity<?> addOrganization(@Valid @RequestBody final OrganizationRequest organizationRequest)
			throws Exception {
		if (organizationRepository.existsByName(organizationRequest.getName())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Organization already exist!!"),
					HttpStatus.CONFLICT);
		}
		logger.info("Adding organization: requested payload [{}]", organizationRequest);

		Organization organization = new Organization(organizationRequest);
		organizationRepository.save(organization);

		return new ResponseEntity<Object>(new ApiResponse(true, "Organization added successfully!!"),
				HttpStatus.CREATED);

	}

	@GetMapping("/organization/{orgId}")
	public ResponseEntity<?> getOrganization(@PathVariable("orgId") Long orgId) throws Exception {
		if (!organizationRepository.existsById(orgId)) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Organization not exist!!"),
					HttpStatus.NO_CONTENT);
		}
		try {
			logger.info("Fetching organization deatils: requested  [orgId: {}]", orgId);
			Organization org = organizationRepository.findById(orgId).get();
			OrganizationResponse orgResponse = new OrganizationResponse(org.getId(), org.getName(), org.getLogo());
			return ResponseEntity.ok(orgResponse);

		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@DeleteMapping("/organization/delete/{orgId}")
	public ResponseEntity<?> deleteOrganization(@Valid @PathVariable("orgId") @NotNull Long orgId) throws Exception {
		if (!organizationRepository.existsById(orgId)) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Organization not exist!!"),
					HttpStatus.BAD_REQUEST);
		}
		try {
			logger.info("Deleting organization: requested  [orgId: {}]", orgId);

			organizationRepository.deleteById(orgId);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

		return ResponseEntity.ok(new ApiResponse(true, "Organization deleted successfully!!"));

	}

	@PutMapping("/organization/update")
	public ResponseEntity<?> updateOrganization(@Valid @RequestBody final OrganizationRequest organizationRequest)
			throws Exception {

		if (!organizationRepository.existsById(organizationRequest.getId())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Organization not exist!!"),
					HttpStatus.BAD_REQUEST);
		}

		try {

			logger.info("Updating organization: requested payload [{}]", organizationRequest);
			Organization org = organizationRepository.findByName(organizationRequest.getName()).orElse(null);
			if (null != org && !org.getId().equals(organizationRequest.getId())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Organization name is already used"),
						HttpStatus.BAD_REQUEST);
			}

			Organization organization = new Organization(organizationRequest);
			organizationRepository.save(organization);

			return new ResponseEntity<Object>(new ApiResponse(true, "Organization added successfully!!"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping("/organization/all")
	public ResponseEntity<?> getAllOrganization() throws Exception {
		logger.info("Fetching all organization");

		try {
			organizationRepository.findAll();

			return ResponseEntity.ok(organizationRepository.findAll());
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping("/role/add")
	public ResponseEntity<?> addRole(@Valid RoleRequest roleRequest) {
		try {
			Role role = new Role(roleRequest.getName());
			roleRepository.save(role);

			return new ResponseEntity<Object>(new ApiResponse(true, "Role created successfully"), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@DeleteMapping("/role/delete/{roleId}")
	public ResponseEntity<?> deleteRole(@Valid @PathVariable("roleId") @NotNull Long roleId) {

		try {
			roleRepository.deleteById(roleId);
			;
			return new ResponseEntity<Object>(new ApiResponse(true, "Role deleted successfully"), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping("/role/all")
	public ResponseEntity<List<RoleResponse>> getAllRoles() {
		try {
			List<RoleResponse> roles = roleRepository.findAll().stream().map(role -> {
				return new RoleResponse(role.getId(), role.getName());
			}).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(roles);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Arrays.asList());
		}

	}

	@PostMapping("/user/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		try {
			if (userRepository.existsByEmailAndOrganizationId(signUpRequest.getEmail(), signUpRequest.getOrgId())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByUsernameAndOrganizationId(signUpRequest.getUsername(),
					signUpRequest.getOrgId())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Username is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (!classesRepository.existsById(signUpRequest.getClassId())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "ClassId doesn't exist"),
						HttpStatus.BAD_REQUEST);
			}

			// Creating user's account
			User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword());

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setClasses(classesRepository.findById(signUpRequest.getClassId()).get());
			user.setRole(roleRepository.findById(signUpRequest.getRoleId()).get());
			user.setOrganization(organizationRepository.findById(signUpRequest.getOrgId()).get());

			userRepository.save(user);

			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "User created successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PutMapping("/user/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		try {
			if (!userRepository.existsById(signUpRequest.getUserId())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "UserId: " + signUpRequest.getUserId() + " doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (organizationRepository.existsById(signUpRequest.getOrgId())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Organization Id : " + signUpRequest.getOrgId() + " doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (roleRepository.existsById(signUpRequest.getRoleId())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Role Id : " + signUpRequest.getOrgId() + " doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}

			User user = new User(signUpRequest);
			user.setOrganization(organizationRepository.findById(signUpRequest.getOrgId()).get());
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			user.setRole(roleRepository.findById(signUpRequest.getRoleId()).get());
			user.setOrganization(organizationRepository.findById(signUpRequest.getOrgId()).get());

			userRepository.save(user);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "User updated successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserResponse> getUser(@Valid @PathVariable("userId") @NotNull Long userId) {
		try {
			if (!userRepository.existsById(userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			User user = userRepository.findById(userId).get();
			return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
					user.getOrganization().getId(), user.getRole().getId(), user.getClasses().getId()));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/user/all/{orgId}")
	public ResponseEntity<List<UserResponse>> getAllUser(@Valid @PathVariable("orgId") @NotNull Long orgId) {
		try {
			if (!organizationRepository.existsById(orgId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			List<UserResponse> users = userRepository.findByOrganizationId(orgId).stream().map(user -> {
				return new UserResponse(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
						user.getOrganization().getId(), user.getRole().getId(), user.getClasses().getId());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/user/all/{orgId}/class/{classId}")
	public ResponseEntity<List<UserResponse>> getUsersByOrgIdAndClass(@Valid @PathVariable("orgId") @NotNull Long orgId,
			@Valid @PathVariable("classId") @NotNull Long classId) {
		try {
			if (!organizationRepository.existsById(orgId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			if (!classesRepository.existsById(classId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			List<UserResponse> users = userRepository.findByOrganizationIdAndClassesId(orgId, classId).stream()
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

	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<?> deleteUser(@Valid @PathVariable("userId") @NotNull Long userId) {
		try {
			if (!userRepository.existsById(userId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "UserId : " + userId + " doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}
			userRepository.deleteById(userId);
			return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/classes/add")
	public ResponseEntity<?> addClass(@Valid ClassesPayload classPayload) {
		try {
			if (!organizationRepository.existsById(classPayload.getOrganization_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Organization is doesn't exist"),
						HttpStatus.BAD_REQUEST);
			}

			if (classesRepository.existsByNameAndOrganizationId(classPayload.getName(),
					classPayload.getOrganization_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Class is already exist"),
						HttpStatus.BAD_REQUEST);
			}
			Classes classes = new Classes(classPayload.getName());

			classes.setOrganization(organizationRepository.findById(classPayload.getOrganization_id()).get());

			classesRepository.save(classes);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "User updated successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return new ResponseEntity<Object>(new ApiResponse(false, AppConstants.EXCEPTION_EXPECTATION_FAILED),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping("/classes/all/{orgId}")
	public ResponseEntity<List<ClassesPayload>> getAllClasses(@Valid @PathVariable("orgId") @NotNull Long OrgId) {
		try {
			if (!organizationRepository.existsById(OrgId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			List<ClassesPayload> classes = classesRepository.findByOrganizationId(OrgId).stream().map(clss -> {
				return new ClassesPayload(clss.getId(), clss.getName(), clss.getOrganization_id());
			}).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(classes);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@GetMapping("/classes/{classId}")
	public ResponseEntity<ClassesPayload> getClasses(@Valid @PathVariable("classId") @NotNull Long classId) {
		try {
			if (!classesRepository.existsById(classId)) {
				logger.debug("classId {} doesn't exists", classId);
				return ResponseEntity.badRequest().build();
			}
			Classes classes = classesRepository.findById(classId).orElse(null);

			ClassesPayload clss = new ClassesPayload(classes.getId(), classes.getName(),
					classes.getOrganization().getId());
			return ResponseEntity.status(HttpStatus.OK).body(clss);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@DeleteMapping("/classes/{classId}")
	public ResponseEntity<?> deleteClass(@Valid @PathVariable("classId") @NotNull Long classId) {
		try {
			if (!classesRepository.existsById(classId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "ClassId " + classId + " doesn't exist"),
						HttpStatus.BAD_REQUEST);
			}
			classesRepository.deleteById(classId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(true, "classId " + classId + " deleted successfull"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@PostMapping("/subject/add")
	public ResponseEntity<?> addSubject(@Valid SubjectRequest subjectRequest) {
		try {
			if (subjectRepository.existsByNameAndClassesId(subjectRequest.getName(), subjectRequest.getClasses_id())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Subject name " + subjectRequest.getName() + " already exist"),
						HttpStatus.BAD_REQUEST);
			}
			Subject subject = new Subject(subjectRequest.getName());
			subject.setClasses(classesRepository.findById(subjectRequest.getClasses_id()).get());
			subjectRepository.save(subject);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(true, "Subject name  " + subjectRequest.getName() + " saved"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@DeleteMapping("/subject/delete/{subjectId}")
	public ResponseEntity<?> deleteSubject(@Valid @PathVariable("subjectId") Long subjectId) {
		try {
			if (!subjectRepository.existsById(subjectId)) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Subject name " + subjectId + " doesn't exist"), HttpStatus.BAD_REQUEST);
			}
			subjectRepository.deleteById(subjectId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(true, "Subject Id  " + subjectId + " deleted"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@GetMapping("/subject/{subjectId}")
	public ResponseEntity<SubjectResponse> getSubject(@Valid @PathVariable("subjectId") Long subjectId) {
		try {
			if (!subjectRepository.existsById(subjectId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Subject subject = subjectRepository.findById(subjectId).get();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new SubjectResponse(subject.getId(), subject.getName(), subject.getClasses().getId()));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@GetMapping("/subject/all/{classesId}")
	public ResponseEntity<List<SubjectResponse>> getAllSubject(@Valid @PathVariable("classesId") Long classesId) {
		try {
			if (!classesRepository.existsById(classesId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			List<SubjectResponse> subjects = subjectRepository.findByClassesId(classesId).stream().map(
					subject -> new SubjectResponse(subject.getId(), subject.getName(), subject.getClasses().getId()))
					.collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(subjects);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();

		}
	}

	@PostMapping("/book/add")
	public ResponseEntity<?> addBook(@Valid BookRequest bookRequest) {
		try {

			if (!subjectRepository.existsById(bookRequest.getSubject_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Subject ID doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (bookRepository.existsByNameAndSubjectId(bookRequest.getName(), bookRequest.getSubject_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Book is already exists"),
						HttpStatus.BAD_REQUEST);
			}

			Book book = new Book(bookRequest.getName(), bookRequest.getAuthor(), bookRequest.getAuthor());
			book.setSubject(subjectRepository.findById(bookRequest.getSubject_id()).get());
			bookRepository.save(book);
			return new ResponseEntity<Object>(new ApiResponse(false, "Book is saved"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<BookResponse> getBook(@Valid @PathVariable("bookId") @NotNull Long bookId) {
		try {
			if (!bookRepository.existsById(bookId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Book book = bookRepository.findById(bookId).get();
			BookResponse bookResponse = new BookResponse(book.getId(), book.getName(), book.getAuthor(),
					book.getDescription(), book.getSubject().getId());
			return ResponseEntity.ok(bookResponse);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("/book/delete/{bookId}")
	public ResponseEntity<?> deleteBook(@Valid @PathVariable("bookId") @NotNull Long bookId) {
		try {
			if (!bookRepository.existsById(bookId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Book ID is not exists"),
						HttpStatus.BAD_REQUEST);
			}
			bookRepository.deleteById(bookId);
			return new ResponseEntity<Object>(new ApiResponse(false, "Book deleted successfully"), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/book/all/{subjectId}")
	public ResponseEntity<List<BookResponse>> getAllBooks(@Valid @PathVariable("subjectId") @NotNull Long subjectId) {
		try {
			if (!subjectRepository.existsById(subjectId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<BookResponse> books = bookRepository.findBySubjectId(subjectId).stream().map(book -> {
				return new BookResponse(book.getId(), book.getName(), book.getAuthor(), book.getDescription(),
						book.getSubject().getId());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(books);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/lesson/add")
	public ResponseEntity<?> addLesson(@Valid LessonRequest lessonRequest) {
		try {
			if (lessonRepository.existsByNameAndBookId(lessonRequest.getName(), lessonRequest.getBook_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Lesson Already Exists"),
						HttpStatus.BAD_REQUEST);
			}
			if (!bookRepository.existsById(lessonRequest.getBook_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Book doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			if (!userRepository.existsById(lessonRequest.getUser_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			Lesson lesson = new Lesson(lessonRequest.getName());
			lesson.setBook(bookRepository.findById(lessonRequest.getBook_id()).get());
			lesson.setUser(userRepository.findById(lessonRequest.getUser_id()).get());
			;
			lessonRepository.save(lesson);
			return ResponseEntity.ok("Lesson saved");
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/lesson/{lessonId}")
	public ResponseEntity<LessonResponse> addLesson(@Valid @PathVariable("lessonId") @NotNull Long lessonId) {
		try {
			if (!lessonRepository.existsById(lessonId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Lesson lesson = lessonRepository.findById(lessonId).get();
			LessonResponse lessonResponse = new LessonResponse(lesson.getId(), lesson.getName(),
					lesson.getBook().getId(), lesson.getUser().getId());
			return ResponseEntity.ok(lessonResponse);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/lesson/book/{bookId}")
	public ResponseEntity<List<LessonResponse>> getAllLesson(@Valid @PathVariable("bookId") @NotNull Long bookId) {
		try {
			if (!bookRepository.existsById(bookId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<LessonResponse> lessons = lessonRepository.findByBookId(bookId).stream().map(lesson -> {
				return new LessonResponse(lesson.getId(), lesson.getName(), lesson.getBook().getId(),
						lesson.getUser().getId());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(lessons);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("/lesson/delete/{lessonId}")
	public ResponseEntity<?> deleteLesson(@Valid @PathVariable("lessonId") @NotNull Long lessonId) {
		try {
			if (!lessonRepository.existsById(lessonId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			lessonRepository.deleteById(lessonId);
			return ResponseEntity.ok(new ApiResponse(true, "Lesson Deleted Successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/notes/add")
	public ResponseEntity<?> addNotes(@Valid NotesRequest notesRequest) {
		try {
			if (!lessonRepository.existsById(notesRequest.getLesson_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Lesson doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			if (!userRepository.existsById(notesRequest.getUser_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			Notes notes = new Notes(notesRequest.getName(), notesRequest.getContent(), notesRequest.getContent_type());
			notes.setLesson(lessonRepository.findById(notesRequest.getLesson_id()).get());
			notes.setUser(userRepository.findById(notesRequest.getUser_id()).get());
			;
			notesRepository.save(notes);
			return ResponseEntity.ok("Notes saved");
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/notes/lesson/{lessonId}")
	public ResponseEntity<List<NotesResponse>> getAllNotes(@Valid @PathVariable("lessonId") @NotNull Long lessonId) {
		try {
			if (!lessonRepository.existsById(lessonId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<NotesResponse> notes = notesRepository.findByLessonId(lessonId).stream().map(note -> {
				return new NotesResponse(note.getId(), note.getName(), note.getContent(), note.getContent_type(),
						note.getLesson().getId(), note.getUser().getId());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(notes);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("/notes/delete/{noteId}")
	public ResponseEntity<?> deleteNotes(@Valid @PathVariable("noteId") @NotNull Long noteId) {
		try {
			if (!notesRepository.existsById(noteId)) {
				return ResponseEntity.ok(new ApiResponse(true, "NoteId doesn't exists"));
			}
			notesRepository.deleteById(noteId);
			return ResponseEntity.ok(new ApiResponse(true, "Lesson Deleted Successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/assignment/add")
	public ResponseEntity<?> addAssignment(@Valid AssignmentRequest assignmentRequest) {
		try {
			if (!lessonRepository.existsById(assignmentRequest.getLesson_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Lesson doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			if (!userRepository.existsById(assignmentRequest.getUser_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			Assignment assigments = new Assignment(assignmentRequest.getName(), assignmentRequest.getContent(),
					assignmentRequest.getContent_type());
			assigments.setLesson(lessonRepository.findById(assignmentRequest.getLesson_id()).get());
			assigments.setUser(userRepository.findById(assignmentRequest.getUser_id()).get());
			assignmentRepository.save(assigments);
			return ResponseEntity.ok("Assigment saved");
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/assignment/lesson/{lessonId}")
	public ResponseEntity<List<AssignmentResponse>> getAllAssignments(
			@Valid @PathVariable("lessonId") @NotNull Long lessonId) {
		try {
			if (!lessonRepository.existsById(lessonId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<AssignmentResponse> assigments = assignmentRepository.findByLessonId(lessonId).stream()
					.map(assignment -> {
						return new AssignmentResponse(assignment.getId(), assignment.getName(), assignment.getContent(),
								assignment.getContent_type(), assignment.getLesson().getId(),
								assignment.getUser().getId());
					}).collect(Collectors.toList());
			return ResponseEntity.ok(assigments);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("/assignment/delete/{assigmnetId}")
	public ResponseEntity<?> deleteAssignment(@Valid @PathVariable("assigmnetId") @NotNull Long assigmnetId) {
		try {
			if (!assignmentRepository.existsById(assigmnetId)) {
				return ResponseEntity.ok(new ApiResponse(true, "AssignmentId doesn't exists"));
			}
			assignmentRepository.deleteById(assigmnetId);
			return ResponseEntity.ok(new ApiResponse(true, "AssignmentId Deleted Successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/discussion/add")
	public ResponseEntity<?> addDiscussion(@Valid DiscussionRequest discussionRequest) {
		try {
			if (!lessonRepository.existsById(discussionRequest.getLesson_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Lesson doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}
			if (!userRepository.existsById(discussionRequest.getUser_id())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"),
						HttpStatus.BAD_REQUEST);
			}

			Discussion discussions = new Discussion(discussionRequest.getName(), discussionRequest.getContent(),
					discussionRequest.getContent_type());
			discussions.setLesson(lessonRepository.findById(discussionRequest.getLesson_id()).get());
			discussions.setUser(userRepository.findById(discussionRequest.getUser_id()).get());
			discussionRepository.save(discussions);
			return ResponseEntity.ok("Assigment saved");
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/discussion/lesson/{lessonId}")
	public ResponseEntity<List<DiscussionResponse>> getAllDiscussion(
			@Valid @PathVariable("lessonId") @NotNull Long lessonId) {
		try {
			if (!lessonRepository.existsById(lessonId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			List<DiscussionResponse> discussions = discussionRepository.findByLessonId(lessonId).stream().map(note -> {
				return new DiscussionResponse(note.getId(), note.getName(), note.getContent(), note.getContent_type(),
						note.getLesson().getId(), note.getUser().getId());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(discussions);
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@DeleteMapping("/discussion/delete/{discussionId}")
	public ResponseEntity<?> deleteDiscussiont(@Valid @PathVariable("discussionId") @NotNull Long discussionId) {
		try {
			if (!discussionRepository.existsById(discussionId)) {
				return ResponseEntity.ok(new ApiResponse(true, "DiscussionId doesn't exists"));
			}
			discussionRepository.deleteById(discussionId);
			return ResponseEntity.ok(new ApiResponse(true, "DiscussionId Deleted Successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/teacher-subject-association/teacher/{teacherId}/subject/{subjectId}")
	public ResponseEntity<?> associateTeacherSubject(@Valid @PathVariable("teacherId") @NotNull Long teacherId,
			@Valid @PathVariable("subjectId") @NotNull Long subjectId) {
		try {
			if (!subjectRepository.existsById(subjectId)) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "Subject name " + subjectId + " doesn't exist"), HttpStatus.BAD_REQUEST);
			}

			if (!userRepository.existsById(teacherId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "UserId: " + teacherId + " doesn't exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (teacherSubjectAssociationRepository.existsBySubjectId(subjectId)) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Subject already associated"),
						HttpStatus.BAD_REQUEST);
			}
			User user = userRepository.findById(teacherId).get();
			if (!RoleName.TEACHER.getIndex().equals(user.getRole().getId())) {
				return new ResponseEntity<Object>(
						new ApiResponse(false, "The UserId is not the teacher, hence not allowing to associate"),
						HttpStatus.BAD_REQUEST);
			}

			TeacherSubjectAssociation association = new TeacherSubjectAssociation();
			association.setUser(userRepository.findById(teacherId).get());
			association.setSubject(subjectRepository.findById(subjectId).get());
			teacherSubjectAssociationRepository.save(association);
			return ResponseEntity.ok(new ApiResponse(true, "Assocation saved successfully"));
		} catch (Exception e) {
			logger.error(AppConstants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/semester/add")
	public ResponseEntity<?> addSemester(@Valid SemesterRequest semesterRequest) {
		if (!classesRepository.existsById(semesterRequest.getClasses_id())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Class doesn't exists"), HttpStatus.BAD_REQUEST);
		}

		if (semesterRepository.existsByNameAndClassesId(semesterRequest.getName(), semesterRequest.getClasses_id())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Semester already exists"),
					HttpStatus.BAD_REQUEST);
		}

		Semester semester = new Semester(semesterRequest.getName());
		semester.setClasses(classesRepository.findById(semesterRequest.getId()).get());
		semesterRepository.save(semester);
		return new ResponseEntity<Object>(new ApiResponse(true, "Semester saved successfully"), HttpStatus.OK);
	}

	@DeleteMapping("/semester/delete/{semId}")
	public ResponseEntity<?> deleteSemester(@Valid @PathVariable("semId") @NotNull Long semId) {
		if (!semesterRepository.existsById(semId)) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Semester doesn't exists"),
					HttpStatus.BAD_REQUEST);
		}
		semesterRepository.deleteById(semId);
		return new ResponseEntity<Object>(new ApiResponse(true, "Semester deleted successfully"), HttpStatus.OK);
	}

	@GetMapping("/semester/all/{classId}")
	public ResponseEntity<List<SemesterResponse>> allSemester(@Valid @PathVariable("classId") @NotNull Long classId) {
		if (!classesRepository.existsById(classId)) {
			return ResponseEntity.badRequest().build();
		}

		List<SemesterResponse> semList = semesterRepository.findByClassesId(classId).stream().map(sem -> {
			return new SemesterResponse(sem.getId(), sem.getName(), sem.getClasses().getId());
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(semList);
	}

	@PostMapping("/semester/marks/update")
	public ResponseEntity<?> updateSemesterMarks(@Valid SemesterMarksRequest semesterMarksRequest) {
		if (!userRepository.existsById(semesterMarksRequest.getUser_id())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "User doesn't Exists"), HttpStatus.BAD_REQUEST);
		}

		if (!semesterRepository.existsById(semesterMarksRequest.getSemester_id())) {
			return new ResponseEntity<Object>(new ApiResponse(false, "Semester doesn't exists"),
					HttpStatus.BAD_REQUEST);
		}

		if (!subjectRepository.existsById(semesterMarksRequest.getSubject_id())) {
			return new ResponseEntity<Object>(
					new ApiResponse(false, "Subject Id " + semesterMarksRequest.getSubject_id() + " doesn't exist"),
					HttpStatus.BAD_REQUEST);
		}

		if (null != semesterMarksRequest.getId() && semesterMarksRepository.existsById(semesterMarksRequest.getId())) {
			semesterMarksRepository.updateSemesterMarks(semesterMarksRequest.getId(),
					semesterMarksRequest.getTotal_marks(), semesterMarksRequest.getObtained_marks(),
					semesterMarksRequest.getPercentage());
		} else {
			SemesterMarks marks = new SemesterMarks(semesterMarksRequest.getTotal_marks(),
					semesterMarksRequest.getObtained_marks(), semesterMarksRequest.getPercentage());
			marks.setSemester(semesterRepository.findById(semesterMarksRequest.getSemester_id()).get());
			marks.setUser(userRepository.findById(semesterMarksRequest.getId()).get());
			marks.setSubject(subjectRepository.findById(semesterMarksRequest.getSubject_id()).get());
			semesterMarksRepository.save(marks);
		}

		return new ResponseEntity<Object>(new ApiResponse(false, "Marks saved successfully"), HttpStatus.OK);
	}

	@DeleteMapping("/semester/marks/delete/{semMarkId}")
	public ResponseEntity<?> deleteSemesterMarks(@Valid @PathVariable("semMarkId") Long semMarkId) {
		if (!semesterMarksRepository.existsById(semMarkId)) {
			return new ResponseEntity<Object>(new ApiResponse(false, "semMarkId doesn't Exists"),
					HttpStatus.BAD_REQUEST);
		}
		semesterMarksRepository.deleteById(semMarkId);
		return new ResponseEntity<Object>(new ApiResponse(false, "Semester marks deleted Successfully"), HttpStatus.OK);
	}
}
