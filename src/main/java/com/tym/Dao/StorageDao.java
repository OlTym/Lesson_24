package com.tym.Dao;

import com.tym.Model.User;

import java.util.List;

public interface StorageDao {

    void removeUser(User user);

    void insertUser(User user);

    void updateUser(User user);

    User getUser(int id);

    User getUserByName(String name);

    List<User> getAllUsers();


}
