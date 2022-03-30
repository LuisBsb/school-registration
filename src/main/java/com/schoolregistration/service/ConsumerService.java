package com.schoolregistration.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.schoolregistration.request.RegisterCourse;
import com.schoolregistration.request.RegisterLikedCourses;
import com.schoolregistration.request.RegisterStudent;

public interface ConsumerService {
	
	ResponseEntity<?> registerCourse(RegisterCourse course);
	
	ResponseEntity<?> registerStudent(RegisterStudent student);
	
	ResponseEntity<?> courses(Pageable pageable);

	ResponseEntity<?> students(Pageable pageable);

	ResponseEntity<?> course(String course);

	ResponseEntity<?> student(Integer registration);

	ResponseEntity<?> coursesWithoutStudent();
	
	ResponseEntity<?> studentsWithoutCourse();

	ResponseEntity<?> coursesWithoutStudents(Pageable pageable);
	
	ResponseEntity<?> studentsByCourse(String course);
	
	ResponseEntity<?> coursesStudent(Integer registration);
	
	ResponseEntity<?> removeStudent(Integer registration);
	
	ResponseEntity<?> removeCourse(String course);
	
	ResponseEntity<?> updateStudent(String newName, Integer registration);
	
	ResponseEntity<?> updateCourse(String newName, String course);
	
	ResponseEntity<?> registerLikedCourses(RegisterLikedCourses likedCourses);

}
