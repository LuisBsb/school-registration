package com.schoolregistration.schoolregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.schoolregistration.SchoolRegistrationApplication;
import com.schoolregistration.controller.CourseController;
import com.schoolregistration.controller.StudentController;
import com.schoolregistration.entity.Course;
import com.schoolregistration.entity.Student;
import com.schoolregistration.exception.ValidacaoException;
import com.schoolregistration.request.RegisterCourse;
import com.schoolregistration.request.RegisterStudent;
import com.schoolregistration.respository.CourseRepository;
import com.schoolregistration.respository.StudentRepository;
import com.schoolregistration.service.ConsumerService;

@SpringBootTest
@AutoConfigureMockMvc
class SchoolRegistrationApplicationTests {

    @Autowired
    private CourseController courseController;
    
    @Autowired
    private ConsumerService service;
    
    @MockBean
    private CourseRepository courseRepository;
    
    @MockBean
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentController studentController;
    
    private Course course;
    
    private Student student;

    @BeforeEach
    public void setup(){
    	course = Course.builder()
                .description("MEDICINE")
                .idCourse(1)
                .students(Set.of(Student.builder().name("FERNANDO").registration(10).idStudent(1).build()))
                .build();
    	
    	student = Student.builder().idStudent(1).name("PEDRO JOÃO").likedCourses(Set.of(course)).registration(10).build();
    }
    
    @Test
    void registerCourse() throws Exception {
        final Optional<Course> optionalCourse = Optional.empty();
        doReturn(optionalCourse).when(courseRepository).findByDescription(anyString());
        doReturn(course).when(courseRepository).save(course);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.CREATED);
        RegisterCourse register = new RegisterCourse();
        register.setCourse("MEDICINE");
        response = courseController.registerCourse(register);        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    
    @Test
    void registerCourseNull() throws ValidacaoException {
    	try {
    		when(courseController.registerCourse(null)).thenThrow(ValidacaoException.class);			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Please provide all necessary data.");
		}
    }
    
    @Test
    void registerCourseisPresent() throws ValidacaoException {
    	final Optional<Course> optionalCourse = Optional.of(course);
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE");
        RegisterCourse register = new RegisterCourse();
        register.setCourse("MEDICINE");
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.CREATED);
        response = courseController.registerCourse(register);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Course already registered.");
    }
    
    @Test
    void registerStudent() throws Exception {
        final Optional<Student> optionalStudent = Optional.empty();
        doReturn(optionalStudent).when(studentRepository).findByRegistration(anyInt());
        doReturn(student).when(studentRepository).save(student);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.CREATED);
        RegisterStudent register = new RegisterStudent();
        register.setName("FERNANDO");
        register.setRegistration(10);
        response = studentController.registerStudent(register);        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    
    @Test
    void registerStudentNull() throws ValidacaoException {
    	try {
    		when(studentController.registerStudent(null)).thenThrow(ValidacaoException.class);			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Please provide all necessary data.");
		}
    }
    
    @Test
    void registerStudentisPresent() throws ValidacaoException {
    	final Optional<Student> optionalStudent = Optional.of(student);
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        RegisterStudent register = new RegisterStudent();
        register.setName("FERNANDO");
        register.setRegistration(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.CREATED);
        response = studentController.registerStudent(register);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Student already registered.");
    }
    
    @Test
    public void main() {
    	SchoolRegistrationApplication.main(new String[] {});
    }
    

}
