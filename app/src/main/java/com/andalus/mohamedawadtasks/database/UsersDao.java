package com.andalus.mohamedawadtasks.database;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM users WHERE eye_color IN(:userColor)")
    List<UsersEntity> findByEyeColor(String userColor);

    @Query("SELECT * FROM users")
    List<UsersEntity> getAll();

    @Insert
    void insertAll(UsersEntity... users);

    @Query("DELETE FROM users")
    void deleteAll();


}
