package com.victor.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record NewStudent(
        @JsonIgnore int id,
        @JsonProperty("Full Name") String name,
        @JsonProperty("Course Name") String course,
        @JsonProperty("Grade Point Average") double gpa) {};
