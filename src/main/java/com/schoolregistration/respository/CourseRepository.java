package com.schoolregistration.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolregistration.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	Optional<Course> findByDescription(String description);
	
	Optional<List<Course>> findByStudentsIdStudent(Integer idStudent);

}
