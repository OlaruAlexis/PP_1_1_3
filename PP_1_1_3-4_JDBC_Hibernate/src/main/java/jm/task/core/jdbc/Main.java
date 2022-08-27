package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Алексей", "Алексеев", (byte) 25);
        userService.saveUser("Алексей", "Олару", (byte) 24);
        userService.saveUser("Александр", "Александров", (byte) 26);

        List<User> strings = userService.getAllUsers();
        for (User s : strings)
            System.out.println(s);

        userService.removeUserById(3);

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}