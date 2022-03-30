package com.schoolregistration.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolregistration.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByRegistration(Integer registration);
	
	Optional<List<Student>> findByLikedCoursesIdCourse(Integer idCourse);
}
