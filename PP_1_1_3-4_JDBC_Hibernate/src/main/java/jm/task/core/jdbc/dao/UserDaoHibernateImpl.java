package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USER " +
                    "(id BIGINT not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))").executeUpdate();
            session.getTransaction().commit();
        }
    }


    @Override
    public void dropUsersTable() {
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USER").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            users = session.createQuery(criteria).getResultList();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(SessionFactory sf = Util.getSessionFactory();
            Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE USER").executeUpdate();
            session.getTransaction().commit();
        }
    }
}