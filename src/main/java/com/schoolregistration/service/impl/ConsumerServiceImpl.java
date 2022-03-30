package com.schoolregistration.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.schoolregistration.entity.Course;
import com.schoolregistration.entity.Student;
import com.schoolregistration.request.RegisterCourse;
import com.schoolregistration.request.RegisterLikedCourses;
import com.schoolregistration.request.RegisterStudent;
import com.schoolregistration.respository.CourseRepository;
import com.schoolregistration.respository.StudentRepository;
import com.schoolregistration.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService{
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Page<Course> coursesWithoutStudents(@PageableDefault(direction = Sort.Direction.ASC,page = 0, size = 100) Pageable pageable) {
		return courseRepository.findAll(pageable);
	}
	
	@Override
	public Page<Course> courses(@PageableDefault(direction = Sort.Direction.ASC,page = 0, size = 100) Pageable pageable) {
		return courseRepository.findAll(pageable);
	}
	
	@Override
	public Page<Student> students(@PageableDefault(direction = Sort.Direction.ASC,page = 0, size = 100) Pageable pageable) {
		return studentRepository.findAll(pageable);
	}
	
	@Override
	public ResponseEntity<?> coursesStudent(Integer registration) {
		String errorMessage = "";
		
		Optional<Student> studentData = studentRepository.findByRegistration(registration);
		
		if( !studentData.isPresent()) {
			errorMessage = "Student not found with registration:" + registration;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		
		Optional<List<Course>> courseData = courseRepository.findByStudentsIdStudent(studentData.get().getIdStudent());
		return new ResponseEntity<>(courseData, HttpStatus.CREATED);
		
	}

	@Override
	public ResponseEntity<?> coursesWithoutStudent() {
		List<Course> courseData = courseRepository.findAll();
		courseData.removeIf(x -> !x.getStudents().isEmpty()); 
		return new ResponseEntity<>(courseData, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> studentsWithoutCourse() {
		List<Student> studentData = studentRepository.findAll();
		studentData.removeIf(x -> !x.getLikedCourses().isEmpty()); 
		return new ResponseEntity<>(studentData, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> studentsByCourse(String course) {
		String errorMessage = "";
		
		Optional<Course> courseData = courseRepository.findByDescription(course.toUpperCase());
		
		if (!courseData.isPresent()) {
			errorMessage = "Course not found with description:" + course;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			Optional<Student> studentData = studentRepository.findByLikedCoursesIdCourse(courseData.get().getIdCourse());
			return new ResponseEntity<>(studentData, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> registerCourse(RegisterCourse course) {
		String errorMessage = "";
		
		course.setCourse(course.getCourse().toUpperCase());
		Optional<Course> courseData = courseRepository.findByDescription(course.getCourse());
		
		if (courseData.isPresent()) {
			errorMessage = "Course already registered.";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			courseRepository.save(Course.builder().description(course.getCourse()).build());
			return new ResponseEntity<>(course, HttpStatus.CREATED);
		}
	}
	
	@Override
	public ResponseEntity<?> registerStudent(RegisterStudent student) {
		String errorMessage = "";
		Optional<Student> studentData = studentRepository.findByRegistration(student.getRegistration());
		
		if (studentData.isPresent()) {
			errorMessage = "Student already registered.";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			studentRepository.save(Student.builder().name(student.getName()).registration(student.getRegistration()).build());
			return new ResponseEntity<>(student, HttpStatus.CREATED);
		}
	}
	
	@Override
	public ResponseEntity<?> student(Integer registration) {
		String errorMessage = "";
		Optional<Student> studentData = studentRepository.findByRegistration(registration);
		
		if (!studentData.isPresent()) {
			errorMessage = "Student not found with registration:" + registration;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(studentData, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> course(String course) {
		String errorMessage = "";
		Optional<Course> courseData = courseRepository.findByDescription(course.toUpperCase());
		
		if (!courseData.isPresent()) {
			errorMessage = "Course not found with description:" + course;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(courseData, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> removeCourse(String course) {
		String errorMessage = "";
		Optional<Course> courseData = courseRepository.findByDescription(course.toUpperCase());
		
		if (!courseData.isPresent()) {
			errorMessage = "Course not found with description:" + course;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			courseRepository.deleteById(courseData.get().getIdCourse());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<?> removeStudent(Integer registration) {
		String errorMessage = "";
		Optional<Student> studentData = studentRepository.findByRegistration(registration);
		
		if (!studentData.isPresent()) {
			errorMessage = "Student not found with registration:" + registration;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			studentRepository.deleteById(studentData.get().getIdStudent());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Override
	public ResponseEntity<?> updateStudent(String newName, Integer registration) {
		String errorMessage = "";
		Optional<Student> studentData = studentRepository.findByRegistration(registration);
		
		if (!studentData.isPresent()) {
			errorMessage = "Student not found with registration:" + registration;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			studentData.get().setName(newName);
			studentRepository.save(studentData.get());
			return new ResponseEntity<>(studentData, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> updateCourse(String newName, String course) {
		String errorMessage = "";
		Optional<Course> courseData = courseRepository.findByDescription(course.toUpperCase());
		
		if (!courseData.isPresent()) {
			errorMessage = "Course not found with description:" + course;
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			courseData.get().setDescription(newName.toUpperCase());
			courseRepository.save(courseData.get());
			return new ResponseEntity<>(courseData, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> registerLikedCourses(RegisterLikedCourses likedCourses) {
		String errorMessage = "";
		Optional<Student> studentData = null;
		Optional<Course> courseData = null;
		
		studentData = studentRepository.findByRegistration(likedCourses.getRegistration());

		courseData = courseRepository.findByDescription(likedCourses.getCourse());
		
		if ( studentData.isPresent() ) {
			if( studentData.get().getLikedCourses().size() == 5 ) {
				errorMessage = "Student has reached the limit of courses.";
			}
		} else {
			errorMessage = "Student not registered.";
		}
		
		if ( courseData.isPresent() ) {
			if( courseData.get().getStudents().size() == 50 ) {
				errorMessage = "Courses has reached the limit of students.";
			}
		} else {
			errorMessage = "Course not registered.";
		}
		
		if( !errorMessage.isEmpty() ) {
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		} else {
			studentData.get().addCourse(courseData.get());
			courseData.get().addStudent(studentData.get());
			courseRepository.save(courseData.get());
			return new ResponseEntity<>(likedCourses, HttpStatus.CREATED);
		}
	}
	
}
