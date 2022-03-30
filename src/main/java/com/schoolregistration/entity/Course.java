package com.schoolregistration.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "COURSE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="course_id_seq")
    @SequenceGenerator(name="course_id_seq", sequenceName="course_id_seq", allocationSize=1)
	@Column(name = "IDCOURSE")
	@ApiModelProperty(hidden = true)
	private Integer idCourse;

	@NonNull
	@Column(name = "DESCRIPTION")
	private String description;

	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "likedCourses")
	private Set<Student> students;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	public void addStudent(Student student) {
		this.students.add(student);
		student.getLikedCourses().add(this);
	}

	public void removeStudent(Integer registration) {
		Student student = this.students.stream().filter(t -> t.getRegistration() == registration).findFirst().orElse(null);
		if (students != null) this.students.remove(student);
		student.getLikedCourses().remove(this);
	}

	public Integer getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(Integer idCourse) {
		this.idCourse = idCourse;
	}

}
