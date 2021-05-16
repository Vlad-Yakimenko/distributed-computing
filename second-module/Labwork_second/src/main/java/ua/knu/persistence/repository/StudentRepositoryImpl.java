package ua.knu.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import ua.knu.persistence.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class StudentRepositoryImpl implements Repository<Student> {

    private Connection connection;

    @Override
    public Collection<Student> findAll() {
        val students = new ArrayList<Student>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {

            while (resultSet.next()) {
                val student = new Student()
                        .setId(resultSet.getInt("id"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setLastName(resultSet.getString("last_name"))
                        .setGroupId(resultSet.getInt("group_id"));

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public boolean save(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO students(first_name, last_name, group_id) VALUES(?,?,?)")) {

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student student) {
        val oldStudent = findById(student.getId());

        if (oldStudent == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE students SET first_name = ?, last_name = ?, group_id = ? WHERE id = ?")) {

            preparedStatement.setString(1, Optional.ofNullable(student.getFirstName()).orElse(oldStudent.getFirstName()));
            preparedStatement.setString(2, Optional.ofNullable(student.getLastName()).orElse(oldStudent.getLastName()));
            preparedStatement.setInt(3, Optional.ofNullable(student.getGroupId()).orElse(oldStudent.getGroupId()));
            preparedStatement.setInt(4, student.getId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student findById(int id) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE id = " + id)) {

            resultSet.next();
            return new Student()
                    .setId(resultSet.getInt("id"))
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .setGroupId(resultSet.getInt("group_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
