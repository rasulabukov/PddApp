package com.example.pddapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User getUser(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);
}