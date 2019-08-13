package com.andalus.mohamedawadtasks.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.andalus.mohamedawadtasks.Repositories.ConstantsRepository;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM " + ConstantsRepository.DATABASE_TABLE_NAME)
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * FROM " + ConstantsRepository.DATABASE_TABLE_NAME+" ORDER BY "+ConstantsRepository.PRIORITY_COLUMN_NAME+" ASC")
    LiveData<List<NoteEntity>> orderByPriority();

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Insert
    void insertNote(NoteEntity noteEntity);

    @Update
    void updateNote(NoteEntity noteEntity);

    @Query("DELETE FROM "+ConstantsRepository.DATABASE_TABLE_NAME)
    void deleteAll();

}
