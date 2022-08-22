package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
        private final static UserService userService = new UserServiceImpl();

        public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
            userService.createUsersTable();

            userService.saveUser("Иван", "Иванов", (byte) 20);
            userService.saveUser("Алексей", "Алексеев", (byte) 25);
            userService.saveUser("Алексей", "Олару", (byte) 24);
            userService.saveUser("Александр", "Александров", (byte) 26);

            userService.removeUserById(3);

            userService.getAllUsers();

            userService.cleanUsersTable();

            userService.dropUsersTable();
        }
    }

