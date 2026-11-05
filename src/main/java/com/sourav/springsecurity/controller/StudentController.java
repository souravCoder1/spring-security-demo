package com.sourav.springsecurity.controller;

import com.sourav.springsecurity.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Collections.unmodifiableList(Arrays.asList(
        new Student(1, "Sourav"),
        new Student(2, "Aman"),
        new Student(3, "Ritik")
    ));

    @GetMapping
    public List<Student> getStudents() {
        return STUDENTS;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
        return STUDENTS.stream()
            .filter(student -> studentId.equals(student.getStudentId()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Student " + studentId + " does not exist"
            ));
    }
}
