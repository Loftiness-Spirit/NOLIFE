package com.example.nolife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE mail = :mail AND password = :password")
    User getUser(String mail, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);
}
