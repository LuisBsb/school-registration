package com.schoolregistration.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.schoolregistration.entity.Course;
import com.schoolregistration.entity.Student;
import com.schoolregistration.request.RegisterCourse;
import com.schoolregistration.request.RegisterLikedCourses;
import com.schoolregistration.request.RegisterStudent;

public interface ConsumerService {
	
	Page<Course> coursesWithoutStudents(Pageable pageable);
	
	Page<Course> courses(Pageable pageable);
	
	ResponseEntity<?> registerCourse(RegisterCourse course);
	
	ResponseEntity<?> registerStudent(RegisterStudent student);
	
	ResponseEntity<?> studentsByCourse(String course);
	
	Page<Student> students(Pageable pageable);
	
	ResponseEntity<?> student(Integer registration);
	
	ResponseEntity<?> course(String course);
	
	ResponseEntity<?> coursesWithoutStudent();
	
	ResponseEntity<?> studentsWithoutCourse();
	
	ResponseEntity<?> coursesStudent(Integer registration);
	
	ResponseEntity<?> removeStudent(Integer registration);
	
	ResponseEntity<?> removeCourse(String course);
	
	ResponseEntity<?> updateStudent(String newName, Integer registration);
	
	ResponseEntity<?> updateCourse(String newName, String course);
	
	ResponseEntity<?> registerLikedCourses(RegisterLikedCourses likedCourses);

}
