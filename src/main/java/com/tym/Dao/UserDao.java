package com.tym.Dao;

import com.tym.Model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDao implements StorageDao {

    public int removeAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        }
    }

    public int removeUser(int userID) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :id");
            query.setParameter("id", userID);
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        }

    }

    public void removeUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public void insertUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    public void updateUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    public User getUser(int userID) {
        User user = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            user = session
                    .createQuery("FROM User WHERE id = :id", User.class)
                    .setParameter("id", userID)
                    .getSingleResult();

        } catch (NoResultException e) {
            {
            }
        }
        return user;
    }

    public User getUserByName(String name) {
        User user = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            user = session
                    .createQuery("FROM User WHERE lower(name) = lower(:name)", User.class)
                    .setParameter("name", name)
                    .getSingleResult();

        } catch (NoResultException e) {
            {
            }
        }
        return user;
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("From User").list();
        }
    }
}
