package repository;

import student.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static database.connection.MysqlDataSource.DATA_SOURCE;

public enum  StudentRepository {

    STUDENT_REPOSITORY;

    public static final String INSERT_STUDENT_SQL = "INSERT into STUDENT (username, email) VALUES (?,?)";


    public void createStudent(Student student) {
        DATA_SOURCE.getConnection().ifPresent(c -> {
            try {
                final PreparedStatement preparedStatement = c.prepareStatement(INSERT_STUDENT_SQL);
                preparedStatement.setString(1, student.getUsername());
                preparedStatement.setString(2, student.getEmail());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }



}
