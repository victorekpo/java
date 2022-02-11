package com.victor.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repositories extend the JPARepository and you pass in the types of the Model and its primary key (ID). The annotation goes above the interface.
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
