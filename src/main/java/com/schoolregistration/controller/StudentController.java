package com.schoolregistration.controller;

import static com.schoolregistration.util.Util.isNull;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.schoolregistration.exception.ValidacaoException;
import com.schoolregistration.request.RegisterLikedCourses;
import com.schoolregistration.request.RegisterStudent;
import com.schoolregistration.service.ConsumerService;

import io.swagger.annotations.Api;


@Api(value = "Allows perform operations on the student entity.")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    ConsumerService consumerService;

    @PostMapping("/registerStudent")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterStudent student) {
    	if(isNull(student) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.registerStudent(student);
    }
    
    @GetMapping("/students")
    public ResponseEntity<?> students(@PageableDefault(direction = Sort.Direction.ASC,page = 0, size = 50) Pageable pageable) {
        return consumerService.students(pageable);
    }
    
    @GetMapping("/student/{registration}")
    public ResponseEntity<?> student(@PathVariable(value = "registration") Integer registration) {
    	if(isNull(registration) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.student(registration);
    }
    
    @GetMapping("/studentsWithoutCourse")
    public ResponseEntity<?> studentsWithoutCourse() {
    	return consumerService.studentsWithoutCourse();
    }
    
    @GetMapping("/students/{course}")
    public ResponseEntity<?> studentsByCourse(@PathVariable(value = "course") String course) {
        return consumerService.studentsByCourse(course);
    }
    
    @DeleteMapping("/removeStudent/{registration}")
    public ResponseEntity<?> removeStudent(@PathVariable(value = "registration") Integer registration) {
    	if(isNull(registration) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.removeStudent(registration);
    }
    
    @PutMapping("/updateStudent/{newName}/{registration}")
    public ResponseEntity<?> updateStudent(@PathVariable(value = "newName") String newName, @PathVariable(value = "registration") Integer registration) {
    	if(isNull(registration) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.updateStudent(newName, registration);
    }
    
    @PostMapping
    @Transactional
    @RequestMapping(value = "/registerLikedCourses", method = RequestMethod.POST)
    public ResponseEntity<?> registerLikedCourses(@RequestBody RegisterLikedCourses course) {
    	if(isNull(course) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.registerLikedCourses(course);
    }
}
