# Student Learning Management System For An Organization

## Assignment

### 1. Ability to register/de-register student to platform
a) Add Student Details (student id, student name, student dob)  
b) Edit Student Details  
c) Delete Student Details  

---

### 2. Ability to register/de-register instructor to platform
a) Add Instructor Details (instructor id, instructor name, INSTRUCTOR dob, COURSE ID)  
b) Edit Instructor Details  
c) Delete Instructor  

---

### 3. Ability to register/de-register course to platform
a) Add Course (courseId, course name, course fee, course instructor)  
b) Delete Course  

---

### 4. Course Registration/Withdraw for Student
a) Student can opt for multiple courses (one student id can have multiple courses)  
b) Delete course at anytime by organisation  

---

### 5. Course Registration/Withdraw for Instructor
a) Instructor can register only for one course  

---

### 6. As a student, I must be able to
a) See the progress of each course (TO_DO | IN_PROGRESS | COMPLETED)  
b) See the student details  
c) Enroll for a new course  
d) Withdraw any course  

---

### 7. As an organization, I must be able to
a) View count of student enrolled in the organisation  
b) View count of student enrolled in each course  
c) View details of instructor for each course  
d) View the count of instructors in organization  
e) All the student details, course details, instructor details by COURSE_ID  
f) All the student details by COURSE STATUS (TO_DO, IN_PROGRESS, COMPLETED)  

---

### 8. As an instructor
a) Course Update Status for student  

---

## Technical Requirements

### 1. PostgreSQL Service
- Implement an application that handles Postgres DB interactions and business logic via valid endpoints  
- Logging using `@Slf4j`  
- Custom Exception Handler using `@RestControllerAdvice`  
- Implement Redis caching  
- Clear/update cache on edit, update, and delete operations  

---

### 2. MongoDB Service
- Implement an application that handles Mongo DB interactions and business logic via valid endpoints  
- Logging using `@Slf4j`  
- Custom Exception Handler using `@RestControllerAdvice`  
- Implement Redis caching  
- Clear/update cache on edit, update, and delete operations  

---

### 3. Cache Implementation
- Implement cache in Mongo or Postgres service  
- Apply caching to only one entity CRUD  

---

### 4. Routing Service
- Create a third service with only one controller  
- Accept an extra parameter (mongo = true/false)  
- If true, hit Mongo service using Feign  
- Else, hit Postgres service using Feign  
- Return the response accordingly  
