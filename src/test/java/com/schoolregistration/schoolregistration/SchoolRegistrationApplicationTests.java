package com.schoolregistration.schoolregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

@SpringBootTest
@AutoConfigureMockMvc
class SchoolRegistrationApplicationTests {

    @Autowired
    private CourseController courseController;
    
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
                .build();
    	
    	student = Student.builder().idStudent(1).name("PEDRO JOÃO").likedCourses(Set.of(course)).registration(10).build();
    }
    
    @Test
    public void main() {
    	SchoolRegistrationApplication.main(new String[] {});
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
    void courses() throws Exception {
    	PageRequest paginacao = PageRequest.of(1, 10);
        List<Course> courseList = Arrays.asList(new Course(), new Course());
        Page<Course> courseListPage = new PageImpl<>(courseList, paginacao, courseList.size());
        doReturn(courseListPage).when(courseRepository).findAll(paginacao);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.courses(paginacao);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void students() throws Exception {
    	PageRequest paginacao = PageRequest.of(1, 10);
        List<Student> studentList = Arrays.asList(new Student(), new Student());
        Page<Student> studentListPage = new PageImpl<>(studentList, paginacao, studentList.size());
        doReturn(studentListPage).when(studentRepository).findAll(paginacao);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.students(paginacao);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void course() throws Exception {
    	final Optional<Course> optionalCourse = Optional.of(course);
    	doReturn(optionalCourse).when(courseRepository).findByDescription(anyString());
    	ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
    	response = courseController.course(anyString());
    	assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void courseNotPresent() throws Exception {
    	final Optional<Course> optionalCourse = Optional.empty();
    	doReturn(optionalCourse).when(courseRepository).findByDescription(anyString());
    	ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    	response = courseController.course(anyString());
    	assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    void courseNull() throws ValidacaoException {
    	try {
    		when(courseController.course(null)).thenThrow(ValidacaoException.class);			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Please provide all necessary data.");
		}
    }
    
    @Test
    void student() throws Exception {
    	final Optional<Student> optionalStudent = Optional.of(student);
    	doReturn(optionalStudent).when(studentRepository).findByRegistration(anyInt());
    	ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
    	response = studentController.student(anyInt());
    	assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void studentNotPresent() throws Exception {
    	final Optional<Student> optionalStudent = Optional.empty();
    	doReturn(optionalStudent).when(studentRepository).findByRegistration(anyInt());
    	ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    	response = studentController.student(anyInt());
    	assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    void studentNull() throws ValidacaoException {
    	try {
    		when(studentController.student(null)).thenThrow(ValidacaoException.class);			
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Please provide all necessary data.");
		}
    }
    
    @Test
    void coursesWithoutStudents() throws Exception {
    	PageRequest paginacao = PageRequest.of(1, 10);
    	List<Course> courseList = Arrays.asList(new Course(), new Course());
    	Page<Course> courseListPage = new PageImpl<>(courseList, paginacao, courseList.size());
    	doReturn(courseListPage).when(courseRepository).findAll(paginacao);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.coursesWithoutStudent();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void studentsWithoutCourse() throws Exception {
        List<Student> studentList = Arrays.asList(student, student);
        doReturn(studentList).when(studentRepository).findAll();
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.studentsWithoutCourse();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void updateCourse() {
    	final Optional<Course> optionalCourse = Optional.of(course);
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.updateCourse("GREYS ANOTOMY", "MEDICINE");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void updateCourseNotPresente() {
    	final Optional<Course> optionalCourse = Optional.empty();
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.updateCourse("GREYS ANOTOMY", "MEDICINE");
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Course not found with description:MEDICINE");
    }
    
    @Test
    void updateStudent() {
    	final Optional<Student> optionalStudent = Optional.of(student);
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.updateStudent("PEDRO SILVA", 10);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void updateStudentNotPresente() {
    	final Optional<Student> optionalStudent = Optional.empty();
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.updateStudent("PEDRO SILVA", 10);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Student not found with registration:10");
    }
    
    @Test
    void removeCourse() {
    	final Optional<Course> optionalCourse = Optional.of(course);
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.removeCourse("MEDICINE");
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void removeCourseNotPresente() {
    	final Optional<Course> optionalCourse = Optional.empty();
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.removeCourse("MEDICINE");
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Course not found with description:MEDICINE");
    }
    
    @Test
    void removeStudent() {
    	final Optional<Student> optionalStudent = Optional.of(student);
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.removeStudent(10);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void removeStudentNotPresente() {
    	final Optional<Student> optionalStudent = Optional.empty();
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        response = studentController.removeStudent(10);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Student not found with registration:10");
    }
    
    @Test
    void coursesStudent() throws Exception {
        final Optional<Student> optionalStudent = Optional.of(student);
        final Optional<List<Course>> optionalCourse =  Optional.of(Arrays.asList(course));
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        doReturn(optionalCourse).when(courseRepository).findByStudentsIdStudent(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = courseController.coursesStudent(10);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void coursesStudentNotPresent() throws Exception {
        final Optional<Student> optionalStudent = Optional.empty();
        final Optional<List<Course>> optionalCourse =  Optional.of(Arrays.asList(course));
        doReturn(optionalStudent).when(studentRepository).findByRegistration(10);
        doReturn(optionalCourse).when(courseRepository).findByStudentsIdStudent(10);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        response = courseController.coursesStudent(10);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Student not found with registration:10");
    }
    
    @Test
    void studentsByCourse() throws Exception {
    	final Optional<Course> optionalCourse = Optional.of(course);
    	final Optional<Student> optionalStudent = Optional.of(student);
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        doReturn(optionalStudent).when(studentRepository).findByLikedCoursesIdCourse(1);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.studentsByCourse("MEDICINE".toUpperCase());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void studentsByCourseNotPresent() throws Exception {
    	final Optional<Course> optionalCourse = Optional.empty();
    	final Optional<Student> optionalStudent = Optional.of(student);
        doReturn(optionalCourse).when(courseRepository).findByDescription("MEDICINE".toUpperCase());
        doReturn(optionalStudent).when(studentRepository).findByLikedCoursesIdCourse(1);
        ResponseEntity<?> response = new ResponseEntity<Object>(HttpStatus.OK);
        response = studentController.studentsByCourse("MEDICINE".toUpperCase());
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "Course not found with description:MEDICINE");
    }
    
}
