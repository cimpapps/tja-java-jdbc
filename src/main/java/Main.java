
import student.Student;

import java.sql.SQLException;

import static repository.StudentRepository.STUDENT_REPOSITORY;


public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {

        Student student = new Student();
        student.setUsername("dan.perescu");
        student.setEmail("dan.perescu@gmail.com");
        STUDENT_REPOSITORY.createStudent(student);
    }


}

