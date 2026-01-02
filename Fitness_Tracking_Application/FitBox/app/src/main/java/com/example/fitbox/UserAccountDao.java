package com.example.fitbox;

import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Insert;


@Dao
public interface UserAccountDao {
    @Insert
    long insertUserAccount(UserAccount userAccount);

    @Query("SELECT * FROM user_account WHERE email = :email AND password = :password")
    UserAccount getUserByEmailAndPassword(String email, String password);

    @Query("SELECT MAX(userId) FROM user_account")
    long getLastInsertedUserId();
}
