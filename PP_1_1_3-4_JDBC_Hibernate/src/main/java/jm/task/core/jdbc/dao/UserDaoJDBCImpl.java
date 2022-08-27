package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = Util.connection(); Statement statement = con.createStatement()) {
            con.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USER"
                    + "(id BIGINT not NULL AUTO_INCREMENT, "
                    + "name VARCHAR(255), "
                    + "lastName VARCHAR(255), "
                    + "age INTEGER, "
                    + "PRIMARY KEY ( id ))");
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.connection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS USER");
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.connection(); PreparedStatement statement =
                connection.prepareStatement("insert into USER (name, lastName, age) values (?, ?, ?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement("delete from USER where id = ?")) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        try (Connection connection = Util.connection(); Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from USER")) {
            connection.setAutoCommit(false);
        while (resultSet.next()) {
            User user =
                    new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
            users.add(user);
        }
        connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.connection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE USER");
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}