package ua.knu.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import ua.knu.persistence.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class GroupRepositoryImpl implements Repository<Group> {

    private Connection connection;

    @Override
    public Collection<Group> findAll() {
        val groups = new ArrayList<Group>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups")) {

            while (resultSet.next()) {
                val group = new Group()
                        .setId(resultSet.getInt("id"))
                        .setGroupName(resultSet.getString("name"))
                        .setCourse(resultSet.getInt("course"));

                groups.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    @Override
    public boolean save(Group group) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT  INTO groups(name, course) VALUES(?,?)")) {

            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getCourse());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Group group) {
        val oldGroup = findById(group.getId());

        if (oldGroup == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE groups SET name = ?, course = ? WHERE id = ?")) {

            preparedStatement.setString(1, Optional.ofNullable(group.getGroupName()).orElse(oldGroup.getGroupName()));
            preparedStatement.setInt(2, Optional.ofNullable(group.getCourse()).orElse(oldGroup.getCourse()));
            preparedStatement.setInt(3, group.getId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Group findById(int id) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups WHERE id = " + id)) {

            resultSet.next();
            return new Group()
                    .setId(resultSet.getInt("id"))
                    .setGroupName(resultSet.getString("name"))
                    .setCourse(resultSet.getInt("course"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM groups WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @SneakyThrows
    public void close() {
        connection.close();
    }
}
