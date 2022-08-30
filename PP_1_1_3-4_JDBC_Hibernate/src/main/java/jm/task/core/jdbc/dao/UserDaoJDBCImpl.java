package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final static Connection con = Util.connection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = con.createStatement()) {
            con.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS USER"
                    + "(id BIGINT not NULL AUTO_INCREMENT, "
                    + "name VARCHAR(255), "
                    + "lastName VARCHAR(255), "
                    + "age INTEGER, "
                    + "PRIMARY KEY ( id ))");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = con.createStatement()) {
            con.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS USER");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try(PreparedStatement statement =
                con.prepareStatement("insert into USER (name, lastName, age) values (?, ?, ?)")) {
            con.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement statement = con.prepareStatement("delete from USER where id = ?")) {
            con.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List <User> users = new ArrayList<>();
        try (Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from USER")) {
            con.setAutoCommit(false);
        while (resultSet.next()) {
            User user =
                    new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
            users.add(user);
        }
        con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = con.createStatement()) {
            con.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE USER");
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.setAutoCommit(false);
        }
    }
}