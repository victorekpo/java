package com.victor.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student student1 = new Student(
                    "Victor",
                    "vic@tor.com",
                    LocalDate.of(1987, Month.APRIL, 9),
                    35);
            Student student2 = new Student(
                    "Queen",
                    "ek@po.com",
                    LocalDate.of(1997, Month.JANUARY, 9),
                    25);

            repository.saveAll(List.of(student1,student2));
        };

    }
}
