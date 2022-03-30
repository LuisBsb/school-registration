package com.schoolregistration.controller;

import static com.schoolregistration.util.Util.isNull;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.schoolregistration.exception.ValidacaoException;
import com.schoolregistration.request.RegisterCourse;
import com.schoolregistration.service.ConsumerService;

import io.swagger.annotations.Api;

@Api(value = "Allows perform operations on the course entity.")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    ConsumerService consumerService;
    
    @PostMapping
    @Transactional
    @RequestMapping(value = "/registerCourse", method = RequestMethod.POST)
    public ResponseEntity<?> registerCourse(@RequestBody RegisterCourse course) {
    	if(isNull(course) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.registerCourse(course);
    }
    
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<?> courses(@PageableDefault(direction = Sort.Direction.ASC,page = 0, size = 50) Pageable pageable) {
        return consumerService.courses(pageable);
    }
    
    @GetMapping("/course/{course}")
    public ResponseEntity<?> course(@PathVariable(value = "course") String course) {
    	if(isNull(course) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.course(course);
    }
    
    @GetMapping("/coursesWithoutStudent")
    public ResponseEntity<?> coursesWithoutStudent() {
    	return consumerService.coursesWithoutStudent();
    }

    @GetMapping("/coursesStudent/{student}")
    public ResponseEntity<?> coursesStudent(@PathVariable(value = "student") Integer student) {
    	if(isNull(student)) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.coursesStudent(student);
    }
    
    @DeleteMapping("/removeCourse/{course}")
    public ResponseEntity<?> removeCourse(@PathVariable(value = "course") String course) {
    	if(isNull(course) ) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.removeCourse(course);
    }
    
    @PutMapping("/updateCourse/{newName}/{course}")
    public ResponseEntity<?> updateCourse(@PathVariable(value = "newName") String newName, @PathVariable(value = "course") String course) {
    	if(isNull(newName) ||  isNull(course)) {
			throw new ValidacaoException("Please provide all necessary data.");
    	}
    	return consumerService.updateCourse(newName, course);
    }

}
