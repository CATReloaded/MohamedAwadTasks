package com.andalus.mohamedawadtasks.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.andalus.mohamedawadtasks.database.NoteDao;
import com.andalus.mohamedawadtasks.database.NoteEntity;
import com.andalus.mohamedawadtasks.database.NotesRoomDatabase;

import java.util.List;

public class NotesDatabaseRepository {

    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;

    public NotesDatabaseRepository(Application application) {
        NotesRoomDatabase db = NotesRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }
    public void insertNote(NoteEntity noteEntity){
        new insertAsyncTask(noteDao).execute(noteEntity);
}
    public void deleteNote(NoteEntity noteEntity){
        new deleteNoteAsyncTask(noteDao).execute(noteEntity);
    }
    public void updateNote(NoteEntity noteEntity){
        new updateNoteAsyncTask(noteDao).execute(noteEntity);
    }
    public void deleteAll() {
        new deleteAllNotesAsyncTask(noteDao).execute();
    }
    public void sortNotes() {
        new sortNotesAsyncTask(noteDao).execute();
    }


    private static class insertAsyncTask extends AsyncTask<NoteEntity , Void , Void>{
        private NoteDao mAsyncTaskNoteDao;
        insertAsyncTask(NoteDao noteDao){
            this.mAsyncTaskNoteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mAsyncTaskNoteDao.insertNote(noteEntities[0]);
            return null;
        }
    }
    private static class deleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao mAsyncTaskNoteDao;
        deleteNoteAsyncTask(NoteDao noteDao){
            this.mAsyncTaskNoteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mAsyncTaskNoteDao.deleteNote(noteEntities[0]);
            return null;
        }
    }
    private static class updateNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao mAsyncTaskNoteDao;
        updateNoteAsyncTask(NoteDao noteDao){
            this.mAsyncTaskNoteDao = noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mAsyncTaskNoteDao.updateNote(noteEntities[0]);
            return null;
        }
    }
    private static class deleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao mAsyncTaskNoteDao;

        deleteAllNotesAsyncTask(NoteDao noteDao) {
            this.mAsyncTaskNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskNoteDao.deleteAll();
            return null;
        }
    }
    private static class sortNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao mAsyncTaskNoteDao;
        sortNotesAsyncTask(NoteDao noteDao){
            this.mAsyncTaskNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskNoteDao.orderByPriority();
            return null;
        }
    }
    }

