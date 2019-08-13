package com.andalus.mohamedawadtasks.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.andalus.mohamedawadtasks.Repositories.ConstantsRepository;

@Database(entities = {NoteEntity.class}, version = 1 , exportSchema = false)
public abstract class NotesRoomDatabase extends RoomDatabase {

    private static NotesRoomDatabase instance;

    public static synchronized NotesRoomDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NotesRoomDatabase.class
                    , ConstantsRepository.DATABASE_TABLE_NAME)
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();


}
