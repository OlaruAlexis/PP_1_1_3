package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SQLException {
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

//        SessionFactory sessionFactory1 = Util.getSessionFactory();
//        System.out.println("sessionFactory1: " + sessionFactory1.hashCode());
//        SessionFactory sessionFactory2 = Util.getSessionFactory();
//        System.out.println("sessionFactory2: " + sessionFactory2.hashCode());
//        SessionFactory sessionFactory3 = Util.getSessionFactory();
//        System.out.println("sessionFactory3: " + sessionFactory3.hashCode());

    }
}