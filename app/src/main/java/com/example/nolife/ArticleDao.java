package com.example.nolife;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAll();

    @Query("SELECT * FROM article WHERE id % 10 = 0")
    LiveData<List<Article>> getFavourites();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Article... articles);

    @Query("DELETE FROM article")
    void deleteAll();
}
