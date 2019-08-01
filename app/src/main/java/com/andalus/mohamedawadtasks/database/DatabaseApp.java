package com.andalus.mohamedawadtasks.database;

import androidx.room.Database;
import androidx.room.Room;
import android.content.Context;
import androidx.room.RoomDatabase;


@Database(entities = {UsersEntity.class} , version = 1 ,exportSchema = false)
public abstract class DatabaseApp extends RoomDatabase {
    final static String TABLE_NAME = "users";

    private static DatabaseApp instance;

    public static synchronized DatabaseApp getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseApp.class, DatabaseApp.TABLE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;

    }

    public abstract UsersDao usersDao();


}
