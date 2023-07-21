package org.example.dao.daoImpl;

import org.example.config.Config;
import org.example.dao.StudentDao;
import org.example.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void createTableStudent() {
        String sql = "c reate table  if not exists students (" +
                "id serial primary key ," +
                "first_name varchar," +
                "last_name varchar," +
                "age int)";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table successfully created");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void saveStudent(Student student) {
        String sql = ""
                + "insert into students(" +
                "first_name,last_name,age)" + "" +
                "values (" +
                "?,?,?)";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());//аранее готовим то что приходит вместо ?
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.executeUpdate();
            System.out.println("successfully saved");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "select * from students";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    @Override
    public void updateStudent(Long id, Student student) {
        String sql = "update students set first_name=?,last_name=?,age=? where id=?;";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Student successfully updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        String sql = "delete from students;";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table successfully cleaned!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = "drop table students;";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is already deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // get by id
    }

    @Override
    public Student getById(Long id, Student student){
        String sql="select * from students where id=?";
        try ( Connection connection = Config.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.setLong(1,id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return student;

    }
}
