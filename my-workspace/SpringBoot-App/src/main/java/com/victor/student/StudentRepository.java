package com.victor.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repositories extend the JPARepository and you pass in the types of the Model and its primary key (ID). The annotation goes above the interface.
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    // Method 1 - JBQL
    @Query("SELECT s FROM Student s WHERE s.email =?1")
    // Method 2 SELECT * FROM student WHERE email = ?
    Optional<Student> findStudentByEmail(String email);
}
