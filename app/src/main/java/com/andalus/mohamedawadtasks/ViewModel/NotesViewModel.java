package com.andalus.mohamedawadtasks.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.andalus.mohamedawadtasks.Repositories.NotesDatabaseRepository;
import com.andalus.mohamedawadtasks.database.NoteEntity;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesDatabaseRepository repository;
    private LiveData<List<NoteEntity>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesDatabaseRepository(application);
        allNotes = repository.getAllNotes();
    }
    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public void insert(NoteEntity noteEntity){
        repository.insertNote(noteEntity);
    }
    public void update(NoteEntity noteEntity){
        repository.updateNote(noteEntity);
    }
    public void deleteNote(NoteEntity noteEntity){
        repository.deleteNote(noteEntity);
    }
    public void deleteAllNotes(){
        repository.deleteAll();
    }
    public void sortNotes(){
        repository.sortNotes();
    }
}
