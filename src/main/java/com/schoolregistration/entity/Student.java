package com.schoolregistration.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "STUDENT")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="student_id_seq")
    @SequenceGenerator(name="student_id_seq", sequenceName="student_id_seq", allocationSize=1)
	@Column(name = "IDSTUDENT")
	@ApiModelProperty(hidden = true)
	private Integer idStudent;

	@NonNull
	@Column(name = "NAME")
	private String name;

	@NonNull
	@Column(name = "REGISTRATION")
	private Integer registration;

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          })
	@JoinTable(
			name = "COURSE_LIKE", 
			joinColumns = @JoinColumn(name = "STUDENT_IDSTUDENT", referencedColumnName = "idStudent"), 
			inverseJoinColumns = @JoinColumn(name = "COURSE_IDCOURSE", referencedColumnName = "idCourse"))
	@JsonIgnore
	private Set<Course> likedCourses = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRegistration() {
		return registration;
	}

	public void setRegistration(Integer registration) {
		this.registration = registration;
	}

	public void addCourse(Course course) {
		this.likedCourses.add(course);
		course.getStudents().add(this);
	}

	public void removeCourse(String course) {
		Course likedCourses = this.likedCourses.stream().filter(t -> t.getDescription().toUpperCase() == course.toUpperCase()).findFirst().orElse(null);
		if (likedCourses != null) this.likedCourses.remove(likedCourses);
		likedCourses.getStudents().remove(this);
	}

	public Integer getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Integer idStudent) {
		this.idStudent = idStudent;
	}

	public Set<Course> getLikedCourses() {
		return likedCourses;
	}

	public void setLikedCourses(Set<Course> likedCourses) {
		this.likedCourses = likedCourses;
	}

}
