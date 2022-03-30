# School Registration
Design and implement simple school registration system

# Swagger
http://localhost:8081/swagger-ui/

# Command to run docker compose
docker-compose up --build -d

# Mysql interface module
http://localhost:8080/

# Some inserts
insert into course values (1, 'MEDICINE');<br />
insert into course values (2, 'LAW');<br />
insert into course values (3, 'IT');<br />
insert into student values (1, 'FERNANDO DE SOUZA', 16632);<br />
insert into student values (2, 'CARLOS DA SILVA', 18995);<br />
insert into student values (3, 'FERNANDO PEREIRA', 36090);<br />
insert into course_like values (1, 1);<br />
insert into course_like values (1, 3);<br />
insert into course_like values (2, 1);<br />
insert into course_like values (2, 2);<br />
insert into course_like values (3, 1);<br />

# Tasks
Filter all courses for a specific student
---------------------------------------
http://localhost:8081/student/students/Medicine

Filter all courses without any students
---------------------------------------
http://localhost:8081/course/coursesWithoutStudent

Filter all students without any courses
---------------------------------------
http://localhost:8081/student/studentsWithoutCourse

Create API for students to register to courses
---------------------------------------
http://localhost:8081/student/registerStudent

Create abilities for user to view all relationships between students and courses
---------------------------------------
http://localhost:8081/course/courses

# INFO
For ease of testing, the H2 configuration is commented out in properties.

