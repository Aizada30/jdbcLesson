package org.example.service;

import org.example.model.Student;

import java.util.List;

public interface StudentService {

        void createTableStudent();

        void saveStudent(Student student);

        List<Student> getAllStudents();

        void updateStudent(Long id,Student student);
        void cleanTable();

        void dropTable();
        Student getById(Long id,Student student);

    }


