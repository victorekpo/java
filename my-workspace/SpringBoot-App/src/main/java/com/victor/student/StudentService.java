package com.victor.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {
    public List<Student> getStudents() {
        Student student1 = new Student(7L,
                "Victor",
                "vic@tor.com",
                LocalDate.of(1987, Month.APRIL, 9),
                25);
        Student student2 = new Student(5L,
                "Ekpo",
                "ek@po.com",
                LocalDate.of(1987, Month.APRIL, 9),
                25);
        return List.of( //can use Array.asList in Java 8-
                student1,
                student2
        );
    }
}
